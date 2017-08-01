package com.young.dxsbangexam.ExamActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.young.dxsbangexam.MyInterface.DownFishListener;
import com.young.dxsbangexam.R;
import com.young.dxsbangexam.Util.DownLoadFile;
import com.young.dxsbangexam.Util.FixWindow;
import com.young.dxsbangexam.Util.LogUtil;
import com.young.dxsbangexam.Util.Subject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;


/**
 * Created by 寮犳垚 on 2016/10/18.
 */

public class ExamChooseActivity extends Activity implements View.OnClickListener {
    private final int mayuanUnitCount = 8;
    private final int sixiuUnitCount = 9;
    private final int shigangUnitCount = 12;
    private final int maogaiUnitCount = 6;
    private RelativeLayout sixiuButton;
    private RelativeLayout maogaiButton;
    private RelativeLayout shigangButton;
    private RelativeLayout mayuanButton;
    private int tempUnitCount = 0;
    private String tempFileDirName;
    private Context mContext;
    private ImageView backImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exam_choose);
        mContext = this;
        new FixWindow(getWindow(), mContext).beauti();
        findView();
        setListener();
    }


    private void findView() {
        backImage = (ImageView) findViewById(R.id.title_left_image);
        backImage.setImageResource(R.drawable.back);
        sixiuButton = (RelativeLayout) findViewById(R.id.sixiu);
        maogaiButton = (RelativeLayout) findViewById(R.id.maogai);
        shigangButton = (RelativeLayout) findViewById(R.id.shigang);
        mayuanButton = (RelativeLayout) findViewById(R.id.mayuan);

    }

    private void setListener() {
        sixiuButton.setOnClickListener(this);
        maogaiButton.setOnClickListener(this);
        shigangButton.setOnClickListener(this);
        mayuanButton.setOnClickListener(this);
        backImage.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String firstPath = null;
        try {
            firstPath = getFilesDir().getCanonicalPath() + "/Dxsbang/";
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("路径", e.toString());
        }
        switch (v.getId()) {
            case R.id.title_left_image:
                finish();
                break;
            case R.id.sixiu: {
                final String sixiuFileDirName = "思修";
                final String sixiuSingleChoice = "mindSingleChoice";
                final String sixiuMultipleChoice = "mindMultipleChoice";
                final String sixiuJudgeChoice = "mindJudgeChoice";
                final String finalFirstPath = firstPath;
                new DownTask(mContext, sixiuUnitCount, sixiuSingleChoice, sixiuMultipleChoice, sixiuJudgeChoice, sixiuFileDirName, new DownFishListener() {
                    @Override
                    public void onFinish() {

                        if (new File(finalFirstPath + sixiuFileDirName + "/" + sixiuSingleChoice + "0.txt").exists()) {
                            ExamDetailsActivity.actionStart(mContext, sixiuSingleChoice, sixiuMultipleChoice, sixiuJudgeChoice, sixiuUnitCount, sixiuFileDirName);
                        } else {
                            Toast.makeText(mContext, "该题库尚未更新，请查看其他题库", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).execute();
            }
            break;


            case R.id.maogai: {
                final String maogaiFileDirName = "毛概";
                final String maogaiSingleChoice = "maoSingleChoice";
                final String maogaiMultipleChoice = "maoMultipleChoice";
                final String maogaiJudgeChoice = "maoJudgeChoice";
                final String finalFirstPath1 = firstPath;
                new DownTask(mContext, maogaiUnitCount, maogaiSingleChoice, maogaiMultipleChoice, maogaiJudgeChoice, maogaiFileDirName, new DownFishListener() {
                    @Override
                    public void onFinish() {
                        if (new File(finalFirstPath1 + maogaiFileDirName + "/" + maogaiSingleChoice + "0.txt").exists()) {
                            ExamDetailsActivity.actionStart(mContext, maogaiSingleChoice, maogaiMultipleChoice, maogaiJudgeChoice, maogaiUnitCount, maogaiFileDirName);
                        } else {
                            Toast.makeText(mContext, "该题库尚未更新，请查看其他题库 ", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).execute();
            }
            break;
            case R.id.shigang: {
                final String shigangDirName = "史纲";
                final String shigangSingleChoice = "historySingleChoice";
                final String shigangMultipleChoice = "historyMultipleChoice";
                final String shigangJudgeChoice = "historyJudgeChoice";
                final String finalFirstPath2 = firstPath;
                new DownTask(mContext, shigangUnitCount, shigangSingleChoice, shigangMultipleChoice, shigangJudgeChoice, shigangDirName, new DownFishListener() {
                    @Override
                    public void onFinish() {
                        if (new File(finalFirstPath2 + shigangDirName + "/" + shigangSingleChoice + "0.txt").exists()) {
                            //存储所有单选,多选,判断题的数组
                            ArrayList<Subject> singleHistoryAllList = new ArrayList<Subject>();
                            ArrayList<Subject> multipleHistoryAllList = new ArrayList<Subject>();
                            ArrayList<Subject> judgeHistoryAllList = new ArrayList<Subject>();
                            //分单元存储的数组
                            ArrayList<Subject>[] singleHistoryList;
                            ArrayList<Subject>[] multipleHistoryList;
                            ArrayList<Subject>[] judgeHistoryList;
                            singleHistoryList = new ArrayList[shigangUnitCount];
                            multipleHistoryList = new ArrayList[shigangUnitCount];
                            judgeHistoryList = new ArrayList[shigangUnitCount];
                            for (int i = 0; i < shigangUnitCount; i++) {//读取文件内容到list里
                                singleHistoryList[i] = getSaveList(shigangDirName, shigangSingleChoice + i + ".txt", false);
                                multipleHistoryList[i] = getSaveList(shigangDirName, shigangMultipleChoice + i + ".txt", false);
                                judgeHistoryList[i] = getSaveList(shigangDirName , shigangJudgeChoice+ i + ".txt", true);
                                singleHistoryAllList.addAll(singleHistoryList[i]);
                                multipleHistoryAllList.addAll(multipleHistoryList[i]);
                                judgeHistoryAllList.addAll(judgeHistoryList[i]);
                            }

                            ExamDetailsActivity.actionStart(mContext, singleHistoryAllList, multipleHistoryAllList, judgeHistoryAllList, singleHistoryList, multipleHistoryList, judgeHistoryList,
                                    shigangUnitCount);

                            // ExamDetailsActivity.actionStart(mContext, shigangSingleChoice, shigangMultipleChoice, shigangJudgeChoice, shigangUnitCount, shigangDirName);
                        } else {
                            Toast.makeText(mContext, "该题库尚未更新，请查看其他题库", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).execute();
            }
            break;
            case R.id.mayuan: {
                tempUnitCount = mayuanUnitCount;
                final String mayuanFileDirName = "马原";
                final String mayuanSingleChoice = "maSingleChoice";
                final String mayuanMultipleChoice = "maMultipleChoice";
                final String mayuanJudgeChoice = "maJudgeChoice";
                final String finalFirstPath3 = firstPath;
                new DownTask(mContext, mayuanUnitCount, mayuanSingleChoice, mayuanMultipleChoice, mayuanJudgeChoice, mayuanFileDirName, new DownFishListener() {
                    @Override
                    public void onFinish() {
                        if (new File(finalFirstPath3 + mayuanFileDirName + "/" + mayuanSingleChoice + "0.txt").exists()) {
                            ExamDetailsActivity.actionStart(mContext, mayuanSingleChoice, mayuanMultipleChoice, mayuanJudgeChoice, mayuanUnitCount, mayuanFileDirName);
                        } else {
                            Toast.makeText(mContext, "该题库尚未更新，请查看其他题库", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).execute();

            }
            default:
        }
    }

    /**
     * 获取一个文件中的题库
     * 注意:!!!!!!
     * 此处的文件名为文件全名,加上后缀和标号     *
     *
     * @param fileName
     * @return
     */
    private ArrayList<Subject> getSaveList(String dirName, String fileName, boolean isJudgeProblem) {
        ArrayList<Subject> unitProblemList = new ArrayList<>();
        try {
            String path = mContext.getFilesDir() + "/Dxsbang/" + dirName + "/";
            File targetFile = new File(path, fileName);
            FileInputStream fileInputStream = new FileInputStream(targetFile);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fileInputStream, "GB2312"));
            boolean firstRead = true;
            String line = "";
            int cirCount = 0;
            String title = new String("空的题目");
            String a = new String("空的选项");
            String b = new String("空的选项");
            String c = new String("空的选项");
            String d = new String("空的选项");
            String answer = new String("暂无答案");
            while ((line = reader.readLine()) != null) {
                if (!"".equals(line.trim())) {
                    if (firstRead) {
                        firstRead = false;
                        continue;
                    }
                    if (isJudgeProblem) {//如果是判断
                        switch (cirCount) {
                            case 0:
                                title = line;
                                break;
                            case 1:
                                answer = line;
                                unitProblemList.add(new Subject(title, answer));
                                break;
                            default:
                                throw new Exception("循环读取数据错误");
                        }
                        cirCount = (cirCount + 1) % 2;
                    } else {//单选或者复选题读取数据
                        switch (cirCount) {
                            case 0:
                                title = line;
                                break;
                            case 1:
                                a = line;
                                break;
                            case 2:
                                b = line;
                                break;
                            case 3:
                                c = line;
                                break;
                            case 4:
                                d = line;
                                break;
                            case 5:
                                answer = line;
                                unitProblemList.add(new Subject(a, b, c, d, title, answer));
                                Log.d("测试", "添加成功了数据");
                                break;
                            default:
                                throw new Exception("循环读取数据错误");
                        }
                        cirCount = (cirCount + 1) % 6;
                    }
                }
            }
            fileInputStream.close();
        } catch (IOException e) {
            e.toString();
            e.printStackTrace();
        } catch (Exception e) {
            e.toString();
            e.printStackTrace();
        }
        return unitProblemList;
/*
        ArrayList<String> list = new ArrayList<String>();
        try {
            String path = mContext.getFilesDir() + "/Dxsbang/" + dirName + "/";
            File targetFile = new File(path, fileName);
            FileInputStream fileInputStream = new FileInputStream(targetFile);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fileInputStream, "GB2312"));
            String line = null;
            boolean firstRead = true;
            try {
                line = "";
            } catch (Exception e) {
                e.printStackTrace();
            }
            while ((line = reader.readLine()) != null) {
                if (!"".equals(line.trim())) {
                    if (firstRead) {
                        firstRead = false;
                        continue;
                    }
                    list.add(line);
                }
            }
            fileInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
        */
    }

    class DownTask extends AsyncTask {
        DownFishListener listener;
        Context mContext;
        int unitCount;
        String singleNames;
        String multipleNames;
        String judgeNames;
        String dirName;
        ProgressDialog progressDialog;

        public DownTask(Context mContext, int unitCount, String singleNames, String multipleName, String judgeName, String dirName, DownFishListener listener) {
            this.mContext = mContext;
            this.unitCount = unitCount;
            this.listener = listener;
            this.singleNames = singleNames;
            this.multipleNames = multipleName;
            this.judgeNames = judgeName;
            this.dirName = dirName;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(mContext);
            progressDialog.setTitle("正在加载题库");
            progressDialog.setMessage("题库加载中,请耐心等待");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected Object doInBackground(Object[] params) {
            try {
                String firstUrl = "http://www.dxsbang.cn/practise/";
                String path = getFilesDir().getCanonicalPath() + "/Dxsbang/";
                File file = new File(path);
                if (!file.exists()) {
                    file.mkdir();
                    Log.d("下载", file.getCanonicalPath());
                }
                for (int i = 0; i < unitCount; i++) {
                    String temp;
                    temp = DownLoadFile.getFile(firstUrl + singleNames + i + ".txt", path + dirName + "/");
                    DownLoadFile.getFile(firstUrl + multipleNames + i + ".txt", path + dirName + "/");
                    DownLoadFile.getFile(firstUrl + judgeNames + i + ".txt", path + dirName + "/");
                    Log.d("下载", temp);
                }
            } catch (IOException e) {
                e.printStackTrace();
                Log.e("下载", e.toString());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            progressDialog.dismiss();
            listener.onFinish();
            super.onPostExecute(o);
        }
    }
}

