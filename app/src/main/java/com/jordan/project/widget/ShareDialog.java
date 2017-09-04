package com.jordan.project.widget;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jordan.project.JordanApplication;
import com.jordan.project.R;
import com.jordan.project.activities.motion.MotionShareActivity;
import com.jordan.usersystemlibrary.UserManager;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;


public class ShareDialog extends Dialog implements
        View.OnClickListener {

    private LinearLayout wechatlinearlayout;
    private LinearLayout pengyoulinearlayout;
    private LinearLayout xinlanglinearlayout;
    private LinearLayout qzonelinearlayout;
    private LinearLayout weibolinearlayout;//dialog_share_weibo
    private TextView cancleTextView;
    private Activity context;
    private String id, img;
    private UserManager userManager;

    public ShareDialog(Activity context, UserManager userManager, String id, String img) {
        super(context, R.style.share_dialog);
        // TODO Auto-generated constructor stub
        this.userManager = userManager;
        this.id = id;
        this.img = img;
        this.context = context;
        setShareDialog();
    }

    public ShareDialog(Context context, int theme) {
        super(context, theme);
        // TODO Auto-generated constructor stub
        this.setCanceledOnTouchOutside(false);
        setContentView(R.layout.dialog_share);
    }

    private void setShareDialog() {
        View view = LayoutInflater.from(getContext()).inflate(
                R.layout.dialog_share, null);
        wechatlinearlayout = (LinearLayout) view
                .findViewById(R.id.dialog_share_wechatlinearlayout);
        pengyoulinearlayout = (LinearLayout) view
                .findViewById(R.id.dialog_share_pengyoulinearlayout);
        xinlanglinearlayout = (LinearLayout) view
                .findViewById(R.id.dialog_share_xinlanglinearlayout);
        qzonelinearlayout = (LinearLayout) view
                .findViewById(R.id.dialog_share_qq_zone);
        weibolinearlayout = (LinearLayout) view
                .findViewById(R.id.dialog_share_weibo);
        cancleTextView = (TextView) view
                .findViewById(R.id.dialog_share_cancletextview);
        wechatlinearlayout.setOnClickListener(this);
        pengyoulinearlayout.setOnClickListener(this);
        xinlanglinearlayout.setOnClickListener(this);
        qzonelinearlayout.setOnClickListener(this);
        cancleTextView.setOnClickListener(this);
        weibolinearlayout.setOnClickListener(this);
        super.setContentView(view);
    }

    // ���÷�������
    public void setShareContent(String content) {
    }

    // ���÷���ͼƬ
    public void setShareImage(String imageUrl) {
    }

    // ����΢��
    public void shareToWeixin() {
        UMImage image = new UMImage(context, JordanApplication.mSharePic);//bitmap文件
        new ShareAction(context).setPlatform(SHARE_MEDIA.WEIXIN).withText("打个球").setCallback(umShareListener).withMedia(image).share();
//		if(!TextUtils.isEmpty(JordanApplication.mSharePic)){
//			new ShareAction(context)
//			.setPlatform(SHARE_MEDIA.WEIXIN)
//			.setCallback(umShareListener)
//			//.withTitle(JordanApplication.mShareTitle)
//			.withText(JordanApplication.mShareDes)
//			.withMedia(
//					new UMImage(context, JordanApplication.mSharePic));
//			//.withTargetUrl(JordanApplication.mShareUrl).share();
//		}else {
//			new ShareAction( context)
//			.setPlatform(SHARE_MEDIA.WEIXIN)
//			.setCallback(umShareListener)
//			//.withTitle(JordanApplication.mShareTitle)
//			.withText(JordanApplication.mShareDes)
//			.withMedia(
//					new UMImage(context, R.mipmap.jordan_logo));
//			//.withTargetUrl(JordanApplication.mShareUrl).share();
//		}
    }

    // ����΢������Ȧ
    public void shareToWeixinCircle() {
        // mController.postShare(context, SHARE_MEDIA.WEIXIN_CIRCLE,
        // posListener);
//		if(!TextUtils.isEmpty(JordanApplication.mSharePic)){
//			new ShareAction( context)
//			.setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE)
//			.setCallback(umShareListener)
//			//.withTitle(JordanApplication.mShareTitle)
//			.withText(JordanApplication.mShareDes)
//			.withMedia(
//					new UMImage(context, JordanApplication.mSharePic));
//			//.withTargetUrl(JordanApplication.mShareUrl).share();
//		}else {
//			new ShareAction( context)
//			.setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE)
//			.setCallback(umShareListener)
//			//.withTitle(JordanApplication.mShareTitle)
//			.withText(JordanApplication.mShareDes)
//			.withMedia(
//					new UMImage(context,R.mipmap.jordan_logo));
//			//.withTargetUrl(JordanApplication.mShareUrl).share();
//		}

        UMImage image = new UMImage(context, JordanApplication.mSharePic);//bitmap文件
        new ShareAction(context).setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE).withText("打个球").setCallback(umShareListener).withMedia(image).share();
    }

    // ��������
//	public void shareToSina() {
//		String sharecontent = "";
//		String shareUrl = "";
//		int lenth = 0;
//		shareUrl = JordanApplication.mShareUrl;
//		sharecontent = JordanApplication.mShareTitle + "\n"
//				+ JordanApplication.mShareDes;
//		lenth = 130;
//		if (sharecontent.length() > lenth) {
//			sharecontent = sharecontent.substring(0, lenth);
//		}
//		if(!TextUtils.isEmpty(JordanApplication.mSharePic)){
//			new ShareAction( context)
//			.setPlatform(SHARE_MEDIA.SINA)
//			.setCallback(umShareListener)
//			.withText(sharecontent)
//			.withMedia(
//					new UMImage(context, JordanApplication.mSharePic))
//			.withTargetUrl(JordanApplication.mShareUrl).share();
//			//Log.i("shareToSina", "withText sharecontent+shareUrl "+sharecontent+"\n"+shareUrl);
//		}else {
//			new ShareAction( context)
//			.setPlatform(SHARE_MEDIA.SINA)
//			.setCallback(umShareListener)
//			.withText(sharecontent)
//			.withMedia(
//					new UMImage(context, R.mipmap.jordan_logo))
//			.withTargetUrl(JordanApplication.mShareUrl).share();
//			//Log.i("shareToSina", "withText sharecontent"+sharecontent);
//		}
//	}

    public void shareToQQ() {
        // mController.postShare(context, SHARE_MEDIA.WEIXIN_CIRCLE,
        // posListener);
//		if(!TextUtils.isEmpty(JordanApplication.mSharePic)){
//			new ShareAction( context)
//					.setPlatform(SHARE_MEDIA.QQ)
//					.setCallback(umShareListener)
//					//.withTitle(JordanApplication.mShareTitle)
//					.withText(JordanApplication.mShareDes)
//					.withMedia(
//							new UMImage(context, JordanApplication.mSharePic));
//					//.withTargetUrl(JordanApplication.mShareUrl).share();
//		}else {
//			new ShareAction( context)
//					.setPlatform(SHARE_MEDIA.QQ)
//					.setCallback(umShareListener)
//					//.withTitle(JordanApplication.mShareTitle)
//					.withText(JordanApplication.mShareDes)
//					.withMedia(
//							new UMImage(context,R.mipmap.jordan_logo));
//					//.withTargetUrl(JordanApplication.mShareUrl).share();
//		}
        UMImage image = new UMImage(context, JordanApplication.mSharePic);//bitmap文件
        new ShareAction(context).setPlatform(SHARE_MEDIA.QQ).withText("打个球").withMedia(image).setCallback(umShareListener).share();
    }

    public void shareToQQZone() {
        // mController.postShare(context, SHARE_MEDIA.WEIXIN_CIRCLE,
        // posListener);
//		if(!TextUtils.isEmpty(JordanApplication.mSharePic)){
//			new ShareAction( context)
//					.setPlatform(SHARE_MEDIA.QQ)
//					.setCallback(umShareListener)
//					//.withTitle(JordanApplication.mShareTitle)
//					.withText(JordanApplication.mShareDes)
//					.withMedia(
//							new UMImage(context, JordanApplication.mSharePic));
//					//.withTargetUrl(JordanApplication.mShareUrl).share();
//		}else {
//			new ShareAction( context)
//					.setPlatform(SHARE_MEDIA.QQ)
//					.setCallback(umShareListener)
//					//.withTitle(JordanApplication.mShareTitle)
//					.withText(JordanApplication.mShareDes)
//					.withMedia(
//							new UMImage(context,R.mipmap.jordan_logo));
//					//.withTargetUrl(JordanApplication.mShareUrl).share();
//		}
        UMImage image = new UMImage(context, JordanApplication.mSharePic);//bitmap文件
        new ShareAction(context).setPlatform(SHARE_MEDIA.QZONE).withText("打个球").setCallback(umShareListener).withMedia(image).share();
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.dialog_share_wechatlinearlayout:
                trainUpload(MotionShareActivity.SHARE_WX);
                shareToWeixin();
                dismiss();
                break;
            case R.id.dialog_share_pengyoulinearlayout:
                trainUpload(MotionShareActivity.SHARE_WX);
                shareToWeixinCircle();
                dismiss();
                break;
            case R.id.dialog_share_xinlanglinearlayout:
                trainUpload(MotionShareActivity.SHARE_QQ);
                shareToQQ();
                dismiss();
                break;
            case R.id.dialog_share_qq_zone:
                trainUpload(MotionShareActivity.SHARE_QQ);
                shareToQQZone();
                dismiss();
                break;
            case R.id.dialog_share_weibo:
                dismiss();
                break;
            case R.id.dialog_share_cancletextview:
                dismiss();
                break;

            default:
                break;
        }
    }

    private void trainUpload(String type) {
        userManager.trainUpload(id, "2", type, "",
                img, "");
    }


    private UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onStart(SHARE_MEDIA share_media) {

        }

        @Override
        public void onResult(SHARE_MEDIA platform) {
            Log.i("UMShareAPI", "onResult");
            if (platform.name().equals("WEIXIN_FAVORITE")) {
                Toast.makeText(context, platform + " 收藏成功啦", Toast.LENGTH_SHORT)
                        .show();
            } else {
                Log.i("UMShareAPI", "分享成功啦");
                Toast.makeText(context, platform + " 分享成功啦", Toast.LENGTH_SHORT)
                        .show();
                context.finish();
            }
        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Log.i("UMShareAPI", "onError");
            Toast.makeText(context, platform + " 分享失败啦", Toast.LENGTH_SHORT)
                    .show();
            if (t != null) {
                Log.d("throw", "throw:" + t.getMessage());
            }
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Log.i("UMShareAPI", "onCancel");
//			Toast.makeText(context, platform + " 分享取消了", Toast.LENGTH_SHORT)
//					.show();
        }
    };
    // // ���������
    // private SnsPostListener posListener = new SnsPostListener() {
    //
    // @Override
    // public void onStart() {
    // // TODO Auto-generated method stub
    // // Toast.makeText(c, "��ʼ����.", Toast.LENGTH_SHORT).show();
    // }
    //
    // @Override
    // public void onComplete(SHARE_MEDIA arg0, int eCode, SocializeEntity arg2)
    // {
    // // TODO Auto-generated method stub
    // if (eCode == 200) {
    // // Toast.makeText(c, "", Toast.LENGTH_SHORT).show();
    // } else {
    // String eMsg = "";
    // if (eCode == -101) {
    // eMsg = "û����Ȩ";
    // }
    // // Toast.makeText(c, "����ʧ��[" + eCode + "] " + eMsg,
    // // Toast.LENGTH_SHORT).show();
    // }
    // }
    // };
    //
    // // ��Ȩ������
    // private UMAuthListener authListener = new UMAuthListener() {
    // @Override
    // public void onStart(SHARE_MEDIA platform) {
    // Toast.makeText(context, "��Ȩ��ʼ", Toast.LENGTH_SHORT).show();
    // }
    //
    // @Override
    // public void onError(SocializeException e, SHARE_MEDIA platform) {
    // Toast.makeText(context, "��Ȩ����", Toast.LENGTH_SHORT).show();
    // }
    //
    // @Override
    // public void onComplete(Bundle value, SHARE_MEDIA platform) {
    // Toast.makeText(context, "��Ȩ���", Toast.LENGTH_SHORT).show();
    // // ��ȡ�����Ȩ��Ϣ������ת���Զ���ķ���༭ҳ��
    // // String uid = value.getString("uid");
    // // mController.postShare(c, SHARE_MEDIA.SINA, mListener);
    // if (platform.equals(SHARE_MEDIA.SINA)) {
    // mController.directShare(context, SHARE_MEDIA.SINA, posListener);
    // } else if (platform.equals(SHARE_MEDIA.TENCENT)) {
    // mController.directShare(context, SHARE_MEDIA.TENCENT,
    // posListener);
    // }
    // }
    //
    // @Override
    // public void onCancel(SHARE_MEDIA arg0) {
    // // TODO Auto-generated method stub
    //
    // }
    // };
}
