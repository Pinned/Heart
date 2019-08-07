package cn.knero.heart;

import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.lihang.smartloadview.SmartLoadingView;

/**
 * @author zhaocheng.luo
 * @since 2019-08-07
 */
public class TypeInputActivity extends AppCompatActivity {

    private static final int DELAY = 150;

    private TextView mMiddleText;
    private SmartLoadingView mLoadingView;

    private StringBuffer mBuffer = new StringBuffer();
    private Handler mHandler = new Handler();

    private String[][] infos = new String[][]{
            new String[]{
                    "致，我最亲爱的宝宝","浮世万千", "吾爱有三", "日，月与卿", "日为朝", "月为暮", "卿为朝朝暮暮", "            -- 师哥"
            },
            new String[]{
                    "To，My love","I love three things in this world .", "Sun, moon and you .", "Sun for morning .", "Moon for night ,", "And you forever .", "                             -- 师哥"
            },
            new String[] {
                    "致，我最亲爱的宝宝","执子之手", "与子偕老", "                  -- 师哥"
            },
            new String[] {
                    "To，My love","To hold your hand", "To grow old with you", "             -- 师哥"
            }
    };
    private int mIndex = -1;
    private int mPrintLine = 0;


    private String mCurrentPrintLine = "";
    private int mPrintCount = 0;

    private int mLineWaitCount = 0;

    private Runnable mPrintContentRunnable = new Runnable() {
        @Override
        public void run() {
            if (mPrintCount == mCurrentPrintLine.length()) {
                // 跳转到下一句
                jumpToNextLine();
            } else {
                if (mPrintCount > 0) {
                    mBuffer.delete(mBuffer.length() - 1, mBuffer.length());
                }

                mBuffer.append(mCurrentPrintLine.substring(mPrintCount, mPrintCount + 1)).append("|");
                mMiddleText.setText(mBuffer.toString());
                mPrintCount += 1;
                mHandler.postDelayed(mPrintContentRunnable, DELAY);
            }
        }
    };

    private Runnable mPrintLineWaiteRunnable = new Runnable() {
        @Override
        public void run() {
            if (mLineWaitCount == 0) {
                if (mBuffer.length() > 0) {
                    mBuffer.delete(mBuffer.length() - 1, mBuffer.length());
                }
                mBuffer.append("\n");
            }
            if (mLineWaitCount > 3) {
                mLineWaitCount = 0;
                startPrint();
            } else {
                if (mLineWaitCount % 2 == 0) {
                    mBuffer.append("|");
                } else {
                    mBuffer.delete(mBuffer.length() - 1, mBuffer.length());
                }
                mMiddleText.setText(mBuffer.toString());
                mHandler.postDelayed(mPrintLineWaiteRunnable, DELAY * 2);
                mLineWaitCount += 1;
            }
        }
    };

    private Runnable mPrintEndRunnable = new Runnable() {
        @Override
        public void run() {
            if (mLineWaitCount > 4) {
                mLineWaitCount = 0;
                mHandler.postDelayed(mDeleteAll, DELAY);
                return;
            }
            if (mLineWaitCount == 0) {
                if (mBuffer.length() > 0) {
                    mBuffer.delete(mBuffer.length() - 1, mBuffer.length());
                }
                mBuffer.append("\n");
            }
            if (mLineWaitCount % 2 == 0) {
                mBuffer.append("|");
            } else {
                mBuffer.delete(mBuffer.length() - 1, mBuffer.length());
            }
            mMiddleText.setText(mBuffer.toString());
            mHandler.postDelayed(mPrintEndRunnable, DELAY * 4);
            mLineWaitCount += 1;
        }
    };

    private Runnable mDeleteAll = new Runnable() {
        @Override
        public void run() {
            if (mBuffer.length() > 1) {
                mBuffer.delete(mBuffer.length() - 2, mBuffer.length());
                mBuffer.append("|");
                mMiddleText.setText(mBuffer.toString());
                mHandler.postDelayed(mDeleteAll, DELAY);
            } else {
                mLoadingView.setVisibility(View.VISIBLE);
                initSlogan();
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type_input);
        mMiddleText = findViewById(R.id.tv_middle);
        mLoadingView = findViewById(R.id.loading_view);
        mLoadingView.setLoginClickListener(new SmartLoadingView.LoginClickListener() {
            @Override
            public void click() {
                goToNextPage();
            }
        });
        mLoadingView.setVisibility(View.INVISIBLE);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                initSlogan();
            }
        }, 1000);
    }

    private void goToNextPage() {
        mLoadingView.loginSuccess(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                Intent intent = new Intent(TypeInputActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.in, R.anim.out);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    private void initSlogan() {
        mBuffer.delete(0, mBuffer.length());
        mPrintLine = 0;
        mIndex++;
        mIndex %= infos.length;
        startPrint();
    }

    private void startPrint() {
        mCurrentPrintLine = infos[mIndex][mPrintLine];
        Log.d("Tag", "startPrint:" + mCurrentPrintLine);
        mPrintCount = 0;
        mHandler.postDelayed(mPrintContentRunnable, DELAY);
    }

    private void jumpToNextLine() {
        Log.d("Tag", "JUMP TO Next Line");
        mPrintLine++;
        if (mPrintLine >= infos[mIndex].length) {
            mHandler.postDelayed(mPrintEndRunnable, DELAY);
        } else {
            mHandler.postDelayed(mPrintLineWaiteRunnable, DELAY);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacks(mPrintLineWaiteRunnable);
        mHandler.removeCallbacks(mPrintContentRunnable);
        mHandler.removeCallbacks(mPrintEndRunnable);
        mHandler.removeCallbacks(mDeleteAll);
    }
}
