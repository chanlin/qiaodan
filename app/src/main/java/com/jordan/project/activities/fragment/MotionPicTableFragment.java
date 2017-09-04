package com.jordan.project.activities.fragment;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.jordan.project.R;
import com.jordan.project.activities.adapter.PathOfParticleAdapter;
import com.jordan.project.content.MotionDetailObserver;
import com.jordan.project.data.MotionDetailData;
import com.jordan.project.database.DatabaseObject;
import com.jordan.project.database.DatabaseProvider;
import com.jordan.project.database.DatabaseService;
import com.jordan.project.entity.LineEntity;
import com.jordan.project.entity.PathOfParticleData;
import com.jordan.project.entity.SpeedData;
import com.jordan.project.utils.HexadecimalUtils;
import com.jordan.project.utils.LogUtils;
import com.jordan.project.utils.TimeUtils;
import com.jordan.project.utils.WindosUtils;
import com.jordan.project.widget.MAChart;
import com.jordan.project.widget.PathOfParticleGridView;
import com.jordan.project.utils.Mat;
import com.jordan.project.utils.Point;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MotionPicTableFragment extends Fragment {

    String serviceId;
    private MotionDetailObserver mMotionDetailObserver;
    private LinearLayout mLLBG;
    ArrayList<Map<Integer, SpeedData>> mapList = new ArrayList<Map<Integer, SpeedData>>();
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MotionDetailObserver.DATABASE_UPDATE:
                    LogUtils.showLog("btnClick", "MotionDetailObserver.DATABASE_UPDATE");
                    //读取数据库
                    initData();
                    break;

                default:
                    break;
            }
        }

        ;
    };

    private void registerContentObservers() {
        mMotionDetailObserver = new MotionDetailObserver(handler);
        Uri uri = Uri.parse("content://" + DatabaseProvider.AUTHOR + "/" + DatabaseObject.MOTION_DETAIL);
        getActivity().getContentResolver().registerContentObserver(uri, true,
                mMotionDetailObserver);
    }
    @SuppressLint({"NewApi", "ValidFragment"})
    public MotionPicTableFragment() {
    }
    @SuppressLint({"NewApi", "ValidFragment"})
    public MotionPicTableFragment(String serviceId) {
        this.serviceId = serviceId;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_motion_pic_table, null);
        registerContentObservers();
        mLLBG = (LinearLayout) view.findViewById(R.id.ll_bg);
        setTheGameView(view);
        setPathOfParticleView(view);
        setMovingSpeedRange(view);
        setLongitudinalJumpAnalysisView(view);
        setJumpPicView(view);
        initData();
        return view;
    }

    private void initData() {
        MotionDetailData motionDetailData = DatabaseService.findMotionDetail(serviceId);
        if (motionDetailData != null) {
            mLLBG.setVisibility(View.VISIBLE);
            initTheGame(motionDetailData);
            initPathOfParticle(motionDetailData);
            initMovingSpeedRange(motionDetailData);
            initLongitudinalJumpAnalysis(motionDetailData);
            initJumpPic(motionDetailData);
        }
    }

    private TextView mTvGameFraction;//综合评估分数
    private SeekBar mSbRunning;//跑动条
    private SeekBar mSbBreach;//突破
    private SeekBar mSbBrounce;//弹跳

    /**
     * 初始化本场比赛数据
     */
    private void setTheGameView(View view) {
        mTvGameFraction = (TextView) view.findViewById(R.id.pic_table_the_game_fraction);
        mSbRunning = (SeekBar) view.findViewById(R.id.seekbar_running);
        mSbBreach = (SeekBar) view.findViewById(R.id.seekbar_breach);
        mSbBrounce = (SeekBar) view.findViewById(R.id.seekbar_bounce);
        mSbRunning.setEnabled(false);
        mSbBreach.setEnabled(false);
        mSbBrounce.setEnabled(false);
    }

    /**
     * 赋值本场比赛数据
     */
    private void initTheGame(MotionDetailData motionDetailData) {
        mTvGameFraction.setText(motionDetailData.getPerfRank() + "分");
        seekBarProgress(mSbRunning, motionDetailData.getRunRank());
        seekBarProgress(mSbBreach, motionDetailData.getBreakRank());
        seekBarProgress(mSbBrounce, motionDetailData.getBounceRank());

    }

    private RelativeLayout mRlPathOfParticle;
    private PathOfParticleGridView mGvPathOfParticle;//轨迹图
    private PathOfParticleAdapter mPathOfParticleAdapter;
    private int maxX = 5;
    private double maxS = 0;
    private double minS = 10;

    /**
     * 初始化轨迹图数据
     */
    private void setPathOfParticleView(View view) {
        LogUtils.showLog("particlelist", "setPathOfParticleView");
        ArrayList<PathOfParticleData> list = new ArrayList<PathOfParticleData>();
        mRlPathOfParticle = (RelativeLayout) view.findViewById(R.id.pic_table_path_of_particle_rl);
        mGvPathOfParticle = (PathOfParticleGridView) view.findViewById(R.id.pic_table_path_of_particle_gv);

        int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        mRlPathOfParticle.measure(w, h);
        int width = mRlPathOfParticle.getMeasuredWidth();
        int height = mRlPathOfParticle.getMeasuredHeight();
        LogUtils.showLog("particlelist", "mRlPathOfParticle.getMeasuredHeight():" + height);
        mPathOfParticleAdapter = new PathOfParticleAdapter(getActivity(), list,width, height, maxX, maxS, minS);
        mGvPathOfParticle.setAdapter(mPathOfParticleAdapter);
        LogUtils.showLog("particlelist", "setPathOfParticleView over");

    }

    /**
     * 赋值轨迹图数据
     */
    private void initPathOfParticle(MotionDetailData motionDetailData) {
        LogUtils.showLog("particlelists", "initPathOfParticle");
        mapList = new ArrayList<Map<Integer, SpeedData>>();
        for (int i = 0; i < 28 * 15; i++) {
            Map<Integer, SpeedData> map = new HashMap<Integer, SpeedData>();
            mapList.add(map);
        }
        LogUtils.showLog("particlelists", "setPathOfParticleView mapList:" + mapList.size());
        ArrayList<PathOfParticleData> list = new ArrayList<PathOfParticleData>();
        //取真实数据tark转换成数组数据
        String tark = motionDetailData.getTrail();
//        String tark = "7472616bc0e9b13d80adbfbf0000403faeb52e00," +
//                "7472616bde2a483f544819c03c4e113f3ed42e00," +
//                "7472616bc329843fa6037abfc55e383f55d62e00," +
//                "7472616bb5d8ac3f88bdc93ef618343f7ad82e00," +
//                "7472616b7fbc1c40758b923fd4542a3fbfda2e00," +
//                "7472616bc45f79407898903f2949393fd3dc2e00," +
//                "7472616b5283a9409ac1483fe6ac383fe9de2e00," +
//                "7472616bb3d9d04060f5673d20d8363f05e12e00," +
//                "7472616b6087cb406621a5bf3fb02d3f3fe32e00," +
//                "7472616bd1b0bb406dc52fc011e2443f2de52e00," +
//                "7472616bde68ae40c02e84c007fb383f42e72e00," +
//                "7472616bc2b297409f97afc0aef7433f33e92e00," +
//                "7472616b86218e40c6b1ddc073563c3f3deb2e00," +
//                "7472616b207185401f1c07c13230453f2aed2e00," +
//                "7472616b36796a4053061ec10971423f20ef2e00," +
//                "7472616b550f4840e88234c1429c403f1cf12e00," +
//                "7472616b0c581a40824449c1f78e3d3f22f32e00," +
//                "7472616b5e747e3f23214fc1106c3b3f2ff52e00," +
//                "7472616bc8f2a8be6e4d45c1cdcf3a3f3ef72e00," +
//                "7472616bbc79bfbf7ae637c183c2373f57f92e00," +
//                "7472616b44fcd3bf58e21fc18438413f51fb2e00," +
//                "7472616b9c4091bf138b09c17cc73e3f53fd2e00," +
//                "7472616b9f6c1bbff319e9c05903353f75ff2e00," +
//                "7472616bd0ca8fbd6109bcc063ea403f70012f00," +
//                "7472616b40d9df3e81a790c02949393f84032f00," +
//                "7472616b55e6523fa48148c0c55e383f9b052f00";
        LogUtils.showLog("particlelists", "setPathOfParticleView tark:" + tark);
        if (tark.contains(",")) {
            String[] tarks = tark.split(",");
            LogUtils.showLog("particlelists", "setPathOfParticleView tarks:" + tarks.length);
            LogUtils.showLog("particlePic", "start");

            ArrayList<Point> xy = new ArrayList<Point>();
            ArrayList<Point> pxy = new ArrayList<Point>();
            if (tarks.length >= 4) {
                for (int a = 0; a < tarks.length; a++) {
                    String as = tarks[a];
                    float trakxf2 = HexadecimalUtils.formatFloatData(as.substring(8, 16));
                    float trakYf = HexadecimalUtils.formatFloatData(as.substring(16, 24));
                    LogUtils.showLog("particlelistStart", "x:" + trakxf2+"|y"+trakYf);
                    Point p = new Point();
                    p.setX(trakxf2);
                    p.setY(trakYf);
                    xy.add(p);
                }
//            for(int k = 0 ; k < xy.size();k++){
//                System.out.println(xy.get(k).getX()+"	"+xy.get(k).getY());
//            }
                for (int i = 0; i < xy.size(); i++) {
                    Point pi = new Point();
                    pxy.add(pi);
                }
                if (xy != null && xy.size() >= 4) {
                    try {
                        //,full 参数 1-全场,0-半场
                        int full = 1;
                        if(motionDetailData.getBallType().equals("2")){
                            full = 0;
                        }
                        Mat.data_analysis_v3(xy, xy.size(), pxy,full);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    pxy = xy;
                }
                for (int j = 0; j < pxy.size(); j++) {
                    System.out.println(pxy.get(j).getX() + "	" + pxy.get(j).getY());
                }
            }
            for (int i = 1; i < tarks.length; i++) {
                int x = 0;
                int y = 0;
                if (tarks.length >= 4 && pxy.size() >= 4) {
                    x = (int) pxy.get(i).getX();
                    y = (int) pxy.get(i).getY();
                    LogUtils.showLog("particlelistOver", "x:" + pxy.get(i).getX()+"|y"+pxy.get(i).getY());
                    x = Math.abs(x);
                    y = Math.abs(y);
                } else {
                    int startX = (int) HexadecimalUtils.formatFloatData(tarks[i].substring(8, 16));
                    int startY = (int) HexadecimalUtils.formatFloatData(tarks[i].substring(16, 24));
                    float floatX = HexadecimalUtils.formatFloatData(tarks[i].substring(8, 16));
                    float floatY = HexadecimalUtils.formatFloatData(tarks[i].substring(16, 24));
                    x = Math.abs(startX);
                    y = Math.abs(startY);
                }
//                int startX = (int) HexadecimalUtils.formatFloatData(tarks[m].substring(8, 16));
//                int startY = (int) HexadecimalUtils.formatFloatData(tarks[m].substring(16, 24));
//                float floatX = HexadecimalUtils.formatFloatData(tarks[m].substring(8, 16));
//                float floatY = HexadecimalUtils.formatFloatData(tarks[m].substring(16, 24));
//                LogUtils.showLog("particlePic", "i:"+i+"=====floatX:" + floatX + "|" + "floatY:" + floatY);
//                //if ((startX * startY) > 0) {
//                    //坐标归类
                if (x > 28) x = 28;
                if (y > 15) y = 14;
                    int number = y * 28 + x;
                    float step = HexadecimalUtils.formatFloatData(tarks[i].substring(24, 32));
                    float aftertime = HexadecimalUtils.formatLongData(tarks[i].substring(32, 40)) * 1000 / 512;
                    float beforetime = HexadecimalUtils.formatLongData(tarks[i - 1].substring(32, 40)) * 1000 / 512;
                    LogUtils.showLog("particlelist", "setPathOfParticleView number:" + number);
                    LogUtils.showLog("particlelist", "setPathOfParticleView mapList:" + mapList.size());
                    Map<Integer, SpeedData> map = mapList.get(number);
                    SpeedData speedData = new SpeedData();
                    speedData.setStep(step);
                    speedData.setTime(aftertime - beforetime);
                    LogUtils.showLog("particlelist", "setPathOfParticleView speedData:" + speedData.toString());
                    map.put(map.size(), speedData);
                    mapList.set(number, map);
                //}
                //}
            }
            LogUtils.showLog("particlePic", "over");
            LogUtils.showLog("particlelist", "setPathOfParticleView mapList:" + mapList.size());
            //转换ArrayList<PathOfParticleData> list
            for (int i = 0; i < mapList.size(); i++) {
                PathOfParticleData pathOfParticleData = new PathOfParticleData();
                Map<Integer, SpeedData> map = mapList.get(i);
                pathOfParticleData.setCount(map.size());
                float step = 0;
                float time = 0;
                for (int key : map.keySet()) {
                    SpeedData speedData = map.get(key);
                    step = step + speedData.getStep();
                    time = time + speedData.getTime();
                }
                double speed = 0;
                if (time != 0) {
                    speed = step * 2  * 3.6 * 1000 / time;
                }
                if (maxX < map.size()) {
                    maxX = map.size();
                }
                if (maxS < speed) {
                    maxS = speed;
                    LogUtils.showLog("particlelists", "maxS:" + maxS);
                }
                if (minS > speed && speed != 0) {
                    minS = speed;
                    LogUtils.showLog("particlelists", "minS:" + minS);
                }
                pathOfParticleData.setAvgSpeed(speed);
                list.add(pathOfParticleData);
                LogUtils.showLog("particlelist", "setPathOfParticleView pathOfParticleData:" + pathOfParticleData.toString());
            }
            LogUtils.showLog("particlelist", "setPathOfParticleView list:" + list.size());
//        for (int i = 0; i < 28 * 15; i++) {
//            PathOfParticleData pathOfParticleData=new PathOfParticleData();
//            if(i%5==0) {
//                pathOfParticleData.setAvgSpeed(1d);
//                pathOfParticleData.setCount(1);
//                list.add(pathOfParticleData);
//            }else if(i%5==1) {
//                pathOfParticleData.setAvgSpeed(5d);
//                pathOfParticleData.setCount(2);
//                list.add(pathOfParticleData);
//            }else if(i%5==2) {
//                pathOfParticleData.setAvgSpeed(9d);
//                pathOfParticleData.setCount(3);
//                list.add(pathOfParticleData);
//            }else if(i%5==3) {
//                pathOfParticleData.setAvgSpeed(14d);
//                pathOfParticleData.setCount(4);
//                list.add(pathOfParticleData);
//            }else if(i%5==4) {
//                pathOfParticleData.setAvgSpeed(17d);
//                pathOfParticleData.setCount(5);
//                list.add(pathOfParticleData);
//            }
//        }
            LogUtils.showLog("particlelist", "initPathOfParticle list.size:" + list.size());
            mPathOfParticleAdapter.updateList(list, 0, maxX, maxS, minS);

        } else {
            mPathOfParticleAdapter.updateList(list, 0, maxX, maxS, minS);
        }

    }

    private MAChart mMAC;//纵跳分析图
    private TextView mTvVerJumpAvgHigh;//平均纵跳高度
    private TextView mTvAvgHoverTime;//平均滞空时间
    private TextView mTvJumpXOne;
    private TextView mTvJumpXTwo;
    private TextView mTvJumpXThree;
    private TextView mTvJumpXFour;
    private TextView mTvJumpXFive;

    /**
     * 初始化纵跳分析数据
     */
    private void setLongitudinalJumpAnalysisView(View view) {
        mMAC = (MAChart) view.findViewById(R.id.machart);
        mTvVerJumpAvgHigh = (TextView) view.findViewById(R.id.pic_table_longitudinal_jump_analysis_ver_jump_avg_high);
        mTvAvgHoverTime = (TextView) view.findViewById(R.id.pic_table_longitudinal_jump_analysis_avg_hover_time);
        mTvJumpXOne = (TextView) view.findViewById(R.id.jump_analysis_x_level_level_one);
        mTvJumpXTwo = (TextView) view.findViewById(R.id.jump_analysis_x_level_level_two);
        mTvJumpXThree = (TextView) view.findViewById(R.id.jump_analysis_x_level_level_three);
        mTvJumpXFour = (TextView) view.findViewById(R.id.jump_analysis_x_level_level_four);
        mTvJumpXFive = (TextView) view.findViewById(R.id.jump_analysis_x_level_level_five);
    }

    /**
     * 赋值纵跳分析数据
     */
    private void initLongitudinalJumpAnalysis(MotionDetailData motionDetailData) {
        mTvVerJumpAvgHigh.setText(new BigDecimal((Double.parseDouble(motionDetailData.getVerJumpAvgHigh()) * 100))
                .setScale(2, BigDecimal.ROUND_DOWN).toString() + "cm");
        mTvAvgHoverTime.setText(new BigDecimal((Double.parseDouble(motionDetailData.getAvgHoverTime()) * 1000))
                .setScale(2, BigDecimal.ROUND_DOWN).toString() + "ms");
        if (motionDetailData.getTotalTime().equals(""))
            motionDetailData.setTotalTime("0");
        double allTime = Double.valueOf(motionDetailData.getTotalTime());
        double xTime = allTime / 4;

        mTvJumpXOne.setText(TimeUtils.formatDateByTimeMS(0));
        mTvJumpXTwo.setText(TimeUtils.formatDateByTimeMS(xTime * 1));
        mTvJumpXThree.setText(TimeUtils.formatDateByTimeMS(xTime * 2));
        mTvJumpXFour.setText(TimeUtils.formatDateByTimeMS(xTime * 3));
        mTvJumpXFive.setText(TimeUtils.formatDateByTimeMS(allTime));
        //time转换成时间（分小时秒搞个工具）

        List<LineEntity> lines = new ArrayList<LineEntity>();

        //计算JUMP
        LineEntity jumpLine = new LineEntity();
        jumpLine.setTitle("jump");
        jumpLine.setLineColor(Color.RED);
        jumpLine.setLineData(initMA(motionDetailData));
        lines.add(jumpLine);

        List<String> ytitle = new ArrayList<String>();
        List<String> xtitle = new ArrayList<String>();

        mMAC.setAxisXColor(0x6A6A6A);
        mMAC.setAxisYColor(0x6A6A6A);
        mMAC.setBorderColor(Color.BLACK);
        mMAC.setAxisMarginTop(10);
        mMAC.setAxisMarginLeft(20);
        mMAC.setAxisYTitles(ytitle);
        mMAC.setAxisXTitles(xtitle);
        mMAC.setLongtitudeFontSize(10);
        mMAC.setLongtitudeFontColor(Color.WHITE);
        mMAC.setLatitudeColor(Color.GRAY);
        mMAC.setLatitudeFontColor(Color.WHITE);
        mMAC.setLongitudeColor(Color.GRAY);
        mMAC.setMaxValue(80);
        mMAC.setMinValue(0);
        mMAC.setMaxPointNum(jumpLine.getLineData().size() - 1);
        mMAC.setDisplayAxisXTitle(true);
        mMAC.setDisplayAxisYTitle(true);
        mMAC.setDisplayLatitude(true);
        mMAC.setDisplayLongitude(true);

        //为chart1增加均线
        mMAC.setLineData(lines);

    }

    private List<Float> initMA(MotionDetailData motionDetailData) {
        LogUtils.showLog("jumpdata", "motionDetailData：" + motionDetailData.toString());
        List<Float> MA5Values = new ArrayList<Float>();
        LogUtils.showLog("jumpdata", "motionDetailData.getVerJumpPoint()：" + motionDetailData.getVerJumpPoint());
        if (motionDetailData.getVerJumpPoint().contains(",")) {
            String[] jumps = null;
            if (motionDetailData.getVerJumpPoint().substring(0, 1).equals(",")) {
                jumps = motionDetailData.getVerJumpPoint().substring(1).split(",");
            } else {
                jumps = motionDetailData.getVerJumpPoint().split(",");
            }
            LogUtils.showLog("jumpdata", "jumps.length：" + jumps.length);
            for (int i = 0; i < jumps.length; i++) {
                MA5Values.add(HexadecimalUtils.formatFloatData(jumps[i].substring(16, 24)));
            }
            LogUtils.showLog("jumpdata", "MA5Values.size：" + MA5Values.size());

        }else if(!motionDetailData.getVerJumpPoint().equals("")){
            MA5Values.add(HexadecimalUtils.formatFloatData(motionDetailData.getVerJumpPoint().substring(16, 24)));
        }
        return MA5Values;
    }


    private TextView mTvMaxSpeed;//pic_table_moving_speed_range_max_speed
    private TextView mTvAvgSpeed;//pic_table_moving_speed_range_avg_speed
    private TextView mTvPercentageFive;//pic_table_moving_speed_range_level_five_percentage
    private TextView mTvPercentageFour;//pic_table_moving_speed_range_level_four_percentage
    private TextView mTvPercentageThree;//pic_table_moving_speed_range_level_three_percentage
    private TextView mTvPercentageTwo;//pic_table_moving_speed_range_level_two_percentage
    private TextView mTvPercentageOne;//pic_table_moving_speed_range_level_one_percentage
    private ImageView mIvPercentageFive;//pic_table_moving_speed_range_level_five_iv
    private ImageView mIvPercentageFour;//pic_table_moving_speed_range_level_four_iv
    private ImageView mIvPercentageThree;//pic_table_moving_speed_range_level_three_iv
    private ImageView mIvPercentageTwo;//pic_table_moving_speed_range_level_two_iv
    private ImageView mIvPercentageOne;//pic_table_moving_speed_range_level_one_iv
    int mPercentageWidth = 0;

    /**
     * 初始化移动速度区间
     */
    private void setMovingSpeedRange(View view) {
        mTvMaxSpeed = (TextView) view.findViewById(R.id.pic_table_moving_speed_range_max_speed);
        mTvAvgSpeed = (TextView) view.findViewById(R.id.pic_table_moving_speed_range_avg_speed);
        mTvPercentageFive = (TextView) view.findViewById(R.id.pic_table_moving_speed_range_level_five_percentage);
        mTvPercentageFour = (TextView) view.findViewById(R.id.pic_table_moving_speed_range_level_four_percentage);
        mTvPercentageThree = (TextView) view.findViewById(R.id.pic_table_moving_speed_range_level_three_percentage);
        mTvPercentageTwo = (TextView) view.findViewById(R.id.pic_table_moving_speed_range_level_two_percentage);
        mTvPercentageOne = (TextView) view.findViewById(R.id.pic_table_moving_speed_range_level_one_percentage);
        mIvPercentageFive = (ImageView) view.findViewById(R.id.pic_table_moving_speed_range_level_five_iv);
        mIvPercentageFour = (ImageView) view.findViewById(R.id.pic_table_moving_speed_range_level_four_iv);
        mIvPercentageThree = (ImageView) view.findViewById(R.id.pic_table_moving_speed_range_level_three_iv);
        mIvPercentageTwo = (ImageView) view.findViewById(R.id.pic_table_moving_speed_range_level_two_iv);
        mIvPercentageOne = (ImageView) view.findViewById(R.id.pic_table_moving_speed_range_level_one_iv);

    }

    /**
     * 赋值移动速度区间
     */
    private void initMovingSpeedRange(MotionDetailData motionDetailData) {
//        ViewTreeObserver vto = mIvPercentageOne.getViewTreeObserver();
//        vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
//            public boolean onPreDraw() {
//                if (mIvPercentageOne.getMeasuredWidth() != 0)
//                    mPercentageWidth = mIvPercentageOne.getMeasuredWidth();
//                return true;
//            }
//        });
        mPercentageWidth = WindosUtils.windowsWidth * 1 / 2;
        LogUtils.showLog("tarkdata", "mPercentageWidth:" + mPercentageWidth);
        mTvAvgSpeed.setText(new BigDecimal((Double.valueOf(motionDetailData.getAvgSpeed()) * 3.6)).
                setScale(2, BigDecimal.ROUND_DOWN).toString() + "km/h");
        mTvMaxSpeed.setText(new BigDecimal((Double.valueOf(motionDetailData.getMaxSpeed()) * 3.6)).
                setScale(2, BigDecimal.ROUND_DOWN).toString() + "km/h");
        String tark = motionDetailData.getTrail();
        //double[] tarks = new double[]{2d,4d,6d,8d,10d,12d,14d,16d,18d,16d,14d,12d,10d,8d};
        if (tark.contains(",")) {
            //解析
            int five = 0;
            int four = 0;
            int three = 0;
            int two = 0;
            int one = 0;
            int zero = 0;
            float maxTime = 0;
            float minTime = 0;
            String maxTarks = "";
            String minTarks = "";
            //遍历
            String[] tarks = tark.split(",");
            for (int i = 1; i < tarks.length; i++) {
                //得到平均速度
                float step = HexadecimalUtils.formatFloatData(tarks[i].substring(24, 32));
                float aftertime = HexadecimalUtils.formatLongData(tarks[i].substring(32, 40)) * 1000 / 512;
                float beforetime = HexadecimalUtils.formatLongData(tarks[i - 1].substring(32, 40)) * 1000 / 512;
                float tarktime = aftertime - beforetime;
                LogUtils.showLog("tarkdatas", "step:" + step);
                LogUtils.showLog("tarkdatas", "tarktime1:" + tarktime);
                if (tarktime > 1500) {
                    tarktime = 1500;
                }
                LogUtils.showLog("tarkdatas", "tarktime2:" + tarktime);
                LogUtils.showLog("tarkdatas", "step * 3.6 * 1000:" + (step * 3.6 * 1000));
                double speed = step * 2  * 3.6 * 1000 / (tarktime);
                //double speed = tarks[i];
                LogUtils.showLog("tarkdata", "speed:" + speed);
                //分类
                if (speed < 3.2) {
                    one = one + 1;
                    LogUtils.showLog("tarkdata", "speed > 2:" + one);
                } else if (speed < 6.4) {
                    two = two + 1;
                    LogUtils.showLog("tarkdata", "speed > 5:" + two);
                } else if (speed < 9.6) {
                    three = three + 1;
                    LogUtils.showLog("tarkdata", "speed > 8:" + three);
                } else if (speed < 12.8) {
                    four = four + 1;
                    LogUtils.showLog("tarkdata", "speed > 12:" + four);
                } else {
                    five = five + 1;
                    LogUtils.showLog("tarkdata", "speed > 16:" + five);
                }
                float time = aftertime;
                if (time > maxTime) {
                    maxTime = time;
                    maxTarks = tarks[i];
                }
                if (minTime == 0 || time < minTime) {
                    minTime = time;
                    minTarks = tarks[i];
                }
            }
            LogUtils.showLog("tarkdatas", "speed < 2:" + zero);
            LogUtils.showLog("tarkdatas", "speed > 2:" + one);
            LogUtils.showLog("tarkdatas", "speed > 5:" + two);
            LogUtils.showLog("tarkdatas", "speed > 8:" + three);
            LogUtils.showLog("tarkdatas", "speed > 12:" + four);
            LogUtils.showLog("tarkdatas", "speed > 16:" + five);
            LogUtils.showLog("dataFormat", "maxTarks: " + maxTarks);
            LogUtils.showLog("dataFormat", "最大时间: " + maxTime);
            LogUtils.showLog("dataFormat", "minTarks: " + minTarks);
            LogUtils.showLog("dataFormat", "最小时间: " + minTime);
            //长度设置
            setImageViewWidth(mIvPercentageOne, mTvPercentageOne, one, (tarks.length-1));
            setImageViewWidth(mIvPercentageTwo, mTvPercentageTwo, two, (tarks.length-1));
            setImageViewWidth(mIvPercentageThree, mTvPercentageThree, three, (tarks.length-1));
            setImageViewWidth(mIvPercentageFour, mTvPercentageFour, four, (tarks.length-1));
            setImageViewWidth(mIvPercentageFive, mTvPercentageFive, five, (tarks.length-1));
        } else {
            //长度设置为0
            setImageViewWidthZero(mIvPercentageOne, mTvPercentageOne);
            setImageViewWidthZero(mIvPercentageTwo, mTvPercentageTwo);
            setImageViewWidthZero(mIvPercentageThree, mTvPercentageThree);
            setImageViewWidthZero(mIvPercentageFour, mTvPercentageFour);
            setImageViewWidthZero(mIvPercentageFive, mTvPercentageFive);
        }
    }

    private void setImageViewWidthZero(ImageView imageView, TextView textView) {
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) imageView.getLayoutParams();
        //获取当前控件的布局对象
        params.width = 0;//设置当前控件布局的高度
        imageView.setLayoutParams(params);//将设置好的布局参数应用到控件中
        textView.setText(0 + "%");
    }

    private void setImageViewWidth(ImageView imageView, TextView textView, int weigth, int all) {
        LogUtils.showLog("tarkdata", "setImageViewWidth weigth:" + weigth);
        LogUtils.showLog("tarkdata", "setImageViewWidth all:" + all);
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) imageView.getLayoutParams();
        //获取当前控件的布局对象
        params.width = mPercentageWidth * weigth / all;//设置当前控件布局的高度
        imageView.setLayoutParams(params);//将设置好的布局参数应用到控件中
        int s = weigth * 100 / all;
        textView.setText(s + "%");
    }

    private TextView mTvJumpPicAvgTouchAngle;//平均翻转角度
    private TextView mTvJumpPicTouchType;//翻转类型
    private TextView mTvAbroadAngle;
    private TextView mTvNormalAngle;
    private TextView mTvWithinAngle;
    private ImageView mIvAbroadAngle;
    private ImageView mIvNormalAngle;
    private ImageView mIvWithinAngle;
    private TextView mTvNowBluetoothFoot;

    /**
     * 初始化纵跳分析图片
     */
    private void setJumpPicView(View view) {
        mTvJumpPicAvgTouchAngle = (TextView) view.findViewById(R.id.pic_table_longitudinal_jump_analysis_avg_touch_angle);
        mTvJumpPicTouchType = (TextView) view.findViewById(R.id.pic_table_longitudinal_jump_analysis_touch_type);
        mTvAbroadAngle = (TextView) view.findViewById(R.id.foot_abroad_angle);
        mTvNormalAngle = (TextView) view.findViewById(R.id.foot_normal_angle);
        mTvWithinAngle = (TextView) view.findViewById(R.id.foot_within_angle);
        mIvAbroadAngle = (ImageView) view.findViewById(R.id.foot_abroad_iv);
        mIvNormalAngle = (ImageView) view.findViewById(R.id.foot_normal_iv);
        mIvWithinAngle = (ImageView) view.findViewById(R.id.foot_within_iv);
        mTvNowBluetoothFoot = (TextView) view.findViewById(R.id.tv_now_bluetooth_foot);


    }

    /**
     * 赋值纵跳分析图片
     */
    private void initJumpPic(MotionDetailData motionDetailData) {
        LogUtils.showLog("progresses:", "motionDetailData:" + motionDetailData.toString());
        mTvJumpPicAvgTouchAngle.setText(motionDetailData.getAvgTouchAngle() + "度");
        int mTouchType = Integer.parseInt(motionDetailData.getTouchType());
        if (motionDetailData.getFooter().equals("R")) {
            if (mTouchType > 0) {
                mTvJumpPicTouchType.setText("外翻");
            } else if (mTouchType < 0) {
                mTvJumpPicTouchType.setText("内翻");
            } else {
                mTvJumpPicTouchType.setText("正常");
            }
            mTvNowBluetoothFoot.setText("(图中为右脚)");
            mIvAbroadAngle.setBackgroundResource(R.mipmap.foot_right_abroad);
            mIvNormalAngle.setBackgroundResource(R.mipmap.foot_right_normal);
            mIvWithinAngle.setBackgroundResource(R.mipmap.foot_right_within);
        } else {
            if (mTouchType > 0) {
                mTvJumpPicTouchType.setText("内翻");
            } else if (mTouchType < 0) {
                mTvJumpPicTouchType.setText("外翻");
            } else {
                mTvJumpPicTouchType.setText("正常");
            }
            mTvNowBluetoothFoot.setText("(图中为左脚)");
            mIvAbroadAngle.setBackgroundResource(R.mipmap.foot_left_abroad);
            mIvNormalAngle.setBackgroundResource(R.mipmap.foot_left_normal);
            mIvWithinAngle.setBackgroundResource(R.mipmap.foot_left_within);
        }
        int abroad = 0;
        int normal = 0;
        int within = 0;
        int all = 0;
        if (motionDetailData.getVerJumpPoint().contains(",")) {
            LogUtils.showLog("progresses:", "contains");
            String[] jumps = null;
            if (motionDetailData.getVerJumpPoint().substring(0, 1).equals(",")) {
                jumps = motionDetailData.getVerJumpPoint().substring(1).split(",");
            } else {
                jumps = motionDetailData.getVerJumpPoint().split(",");
            }
            LogUtils.showLog("progresses:", "jumps.length:" + jumps.length);
            for (int i = 0; i < jumps.length; i++) {
                String key = jumps[i];
                LogUtils.showLog("progresses:", "key:" + key);
                if (HexadecimalUtils.formatFloatData(key.substring(24, 32)) > 0) {
                    abroad = abroad + 1;
                    LogUtils.showLog("progresses:", "abroad:" + abroad);
                } else if (HexadecimalUtils.formatFloatData(key.substring(24, 32)) < 0) {
                    within = within + 1;
                    LogUtils.showLog("progresses:", "within:" + within);
                } else {
                    normal = normal + 1;
                    LogUtils.showLog("progresses:", "normal:" + normal);
                }
                all = all + 1;
                LogUtils.showLog("progresses:", "all:" + all);
            }
            if (motionDetailData.getFooter().equals("R")) {
                LogUtils.showLog("progresses:", "mTvAbroadAngle");
                mTvAbroadAngle.setText((abroad * 100 / all) + "%");
                LogUtils.showLog("progresses:", "mTvNormalAngle");
                mTvNormalAngle.setText((normal * 100 / all) + "%");
                LogUtils.showLog("progresses:", "mTvWithinAngle");
                mTvWithinAngle.setText((within * 100 / all) + "%");
            } else {
                LogUtils.showLog("progresses:", "mTvAbroadAngle");
                mTvAbroadAngle.setText((within * 100 / all) + "%");
                LogUtils.showLog("progresses:", "mTvNormalAngle");
                mTvNormalAngle.setText((normal * 100 / all) + "%");
                LogUtils.showLog("progresses:", "mTvWithinAngle");
                mTvWithinAngle.setText((abroad * 100 / all) + "%");
            }
        } else if (!motionDetailData.getVerJumpPoint().equals("")) {
            String key = motionDetailData.getVerJumpPoint();
            LogUtils.showLog("progresses:", "key:" + key);
            if (HexadecimalUtils.formatFloatData(key.substring(24, 32)) > 0) {
                abroad = abroad + 1;
                LogUtils.showLog("progresses:", "abroad:" + abroad);
            } else if (HexadecimalUtils.formatFloatData(key.substring(24, 32)) < 0) {
                within = within + 1;
                LogUtils.showLog("progresses:", "within:" + within);
            } else {
                normal = normal + 1;
                LogUtils.showLog("progresses:", "normal:" + normal);
            }
            all = all + 1;
            LogUtils.showLog("progresses:", "all:" + all);
            if (motionDetailData.getFooter().equals("R")) {
                LogUtils.showLog("progresses:", "mTvAbroadAngle");
                mTvAbroadAngle.setText((abroad * 100 / all) + "%");
                LogUtils.showLog("progresses:", "mTvNormalAngle");
                mTvNormalAngle.setText((normal * 100 / all) + "%");
                LogUtils.showLog("progresses:", "mTvWithinAngle");
                mTvWithinAngle.setText((within * 100 / all) + "%");
            } else {
                LogUtils.showLog("progresses:", "mTvAbroadAngle");
                mTvAbroadAngle.setText((within * 100 / all) + "%");
                LogUtils.showLog("progresses:", "mTvNormalAngle");
                mTvNormalAngle.setText((normal * 100 / all) + "%");
                LogUtils.showLog("progresses:", "mTvWithinAngle");
                mTvWithinAngle.setText((abroad * 100 / all) + "%");
            }
        }
    }

    private void seekBarProgress(SeekBar seekBar, String state) {
        if (state.equalsIgnoreCase("nan"))
            state = "0";
        if (state.contains(".")) state = state.substring(0, state.indexOf("."));
        int states = Integer.parseInt(state);
        seekBar.setProgress(states);
    }
}
