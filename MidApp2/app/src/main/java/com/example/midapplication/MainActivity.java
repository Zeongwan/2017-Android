package com.example.midapplication;

import android.Manifest;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Parcel;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE };

    private String[] nameArray = {"刘备","关羽", "周瑜","曹操","诸葛亮","吕布","貂蝉","郭嘉","孙权","夏侯渊"};
    private String[] sexArray = {"男","男","男","男","男","男","男","男","男","男"};
    private String[] bdDateArray = {"161-223","?-219","175-210","155-220","181-234","?-198","?-?","170-207","182-252","?-219"};
    private String[] homeArray = {"幽州涿郡涿(河北保定市涿州)","司隶河东郡解(山西运城市临猗县西南)","扬州庐江郡舒(安徽合肥市庐江县西南)","豫州沛国谯(安徽亳州市亳县)","徐州琅邪国阳都(山东临沂市沂南县南)","并州五原郡九原(内蒙古包头市九原区麻池镇西北古城遗址)","暂无相关记载","豫州颍川郡阳翟(河南许昌市禹州)","扬州吴郡富春（浙江杭州市富阳）","豫州沛国谯(安徽亳州市亳县)"};
    private String[] nationsArray = {"蜀","蜀","吴","魏","蜀","东汉","东汉","魏","吴","魏"};
    private String[] introArray = {"蜀汉的开国皇帝，相传是汉景帝之子中山靖王刘胜的后代。赤壁之战之际，刘备联吴抗曹，取得胜利，从东吴处“借”到荆州，迅速发展起来，吞并益州，占领汉中，建立蜀汉政权。病逝于白帝城，临终托孤于诸葛亮。",
    "前将军。与张飞追随刘备征战。后因孙权背盟投曹，后方为吕蒙所破，关羽便退兵，但终为孙权军所擒杀。"
            , "自幼与孙策交好，孙策于袁术麾下初崛起时曾随之扫荡江东，为中郎将。孙策遇刺身亡后，周瑜与张昭一起共同辅佐孙权，为中护军，执掌军政大事。周瑜在江陵进行军事准备时死于巴陵，时年三十六岁。孙权曾为其素服吊丧。"
            ,"政治家、军事家、诗人，统一了北方、挟天子以令诸侯，戎马一生。曹操谥武王，曹丕称帝后，追尊他为武皇帝，史称魏武帝。葬于高陵。","政治家、军事家，被誉为“千古良相”的典范。蜀汉建立，拜为丞相。不幸因积劳成疾而逝世，享年五十四岁，谥曰忠武侯。其“鞠躬尽力，死而后已”的高尚品格，千百年来一直为人们所敬仰和怀念。"
            , "长骑射，武力过人，闻名于并州。董卓入京后，诱使吕布杀死丁原，率其众来投。布虽骁猛，然无谋而多猜忌，又信妻言，不纳群下之言。曹操堑围三月，吕布军上下离心，其将侯成、宋宪、魏续缚陈宫，将其众降；吕布亦就缚，与陈宫、高顺被戮于白门楼。"
            ,"舍身报国的可敬女子，她为了挽救天下黎民，为了推翻权臣董卓的荒淫统治，受王允所托，上演了可歌可泣的连环计（连环美人计），周旋于两个男人之间，成功的离间了董卓和吕布，最终吕布将董卓杀死，结束了董卓专权的黑暗时期。"
            ,"曹操之司空军祭酒。因水土不服，气候恶劣，日夜急行操劳过度而病死。曹操深感痛惜，泪流满面。"
            ,"孙权19岁就继承了其兄孙策之位，力据江东，击败了黄祖。后东吴联合刘备，在赤壁大战击溃了曹操军。东吴后来又和曹操军在合肥附近鏖战，并从刘备手中夺回荆州、杀死关羽、大破刘备的讨伐军。曹丕称帝后孙权先向北方称臣，后自己建吴称帝，迁都建业。"
            ,"夏侯婴之后、大将军惇族弟。自曹操陈留起兵起，便跟随征伐，历任陈留、颍川太守。而后夏侯渊督张郃、徐晃等留守汉中，与前来取汉中的刘备大军交战，为刘备将黄忠所袭，不幸战死。谥曰愍侯。魏齐王正始四年从祀曹操庙庭。"};
    private int[] picIdArray = {R.drawable.a, R.drawable.b, R.drawable.c, R.drawable.d, R.drawable.e, R.drawable.f, R.drawable.g, R.drawable.h, R.drawable.i, R.drawable.j};
    private Vector<String> name, sex, bdDate, home, nations, intro, picPath;
    private Vector<Integer> picId;
    final private int STARTLIST = 1;
    final private int NOADD = 2;
    final private int MAKEADD = 3;
    private ListView listView;
    private ServiceConnection serviceConnection;
    private MusicService.MyBinder myBinder;
    private MusicService musicService;
    // 申请动态权限
    public static void verifyStoragePermissions(Activity activity) {
        int permission = ActivityCompat.checkSelfPermission(activity,
                Manifest.permission.READ_EXTERNAL_STORAGE);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE);
        }
    }
    private void init() {
        findId();
        verifyStoragePermissions(this);
        name = new Vector<String>();
        sex = new Vector<String>();
        bdDate = new Vector<String>();
        home = new Vector<String>();
        nations = new Vector<String>();
        intro = new Vector<String>();
        picPath = new Vector<String>();
        picId = new Vector<Integer>();
        for (int i = 0; i < 10; i++) {
            name.addElement(nameArray[i]);
            sex.addElement(sexArray[i]);
            bdDate.addElement(bdDateArray[i]);
            home.addElement(homeArray[i]);
            nations.addElement(nationsArray[i]);
            intro.addElement(introArray[i]);
            picId.addElement(picIdArray[i]);
            picPath.addElement("");
        }
        // 绑定service
        serviceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                myBinder = (MusicService.MyBinder) iBinder;
                musicService = myBinder.getService();
            }
            @Override
            public void onServiceDisconnected(ComponentName componentName) {
            }
        };
        Intent intent = new Intent(this, MusicService.class);
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
        startService(intent);
    }
    private void findId() {
        Button addButton = (Button) findViewById(R.id.add);
        Button showButton = (Button) findViewById(R.id.showList);
        Button playButton = (Button) findViewById(R.id.playMusic);
        Button findButton = (Button) findViewById(R.id.find);
        final ConstraintLayout initPage = (ConstraintLayout) findViewById(R.id.initPage);
        listView = (ListView) findViewById(R.id.listView);
        showButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listView.getVisibility() == View.INVISIBLE) {
                    listView.setVisibility(View.VISIBLE);
                    initPage.setVisibility(View.INVISIBLE);
                } else {
                    listView.setVisibility(View.INVISIBLE);
                    initPage.setVisibility(View.VISIBLE);
                }
            }
        });
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(MainActivity.this, addActivity.class), MAKEADD);
            }
        });
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    int code = 101;
                    Parcel data = Parcel.obtain();
                    Parcel reply = Parcel.obtain();
                    myBinder.transact(code, data, reply, 0);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void dealAdapter() {
        final List<Map<String, Object>> list = new ArrayList<>();
        for (int i = 0; i < name.size(); i++) {
            Map<String, Object> temp = new LinkedHashMap<>();
            temp.put("itemLetter", (name.elementAt(i).charAt(0)));
            temp.put("itemName", name.elementAt(i));
            list.add(temp);
        }
        final SimpleAdapter simpleAdapter = new SimpleAdapter(this, list, R.layout.item, new String[] {"itemLetter", "itemName"}, new int[] {R.id.itemLetter, R.id.itemName});
        listView.setAdapter(simpleAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this, DetailedPage.class);
                Bundle bundle = new Bundle();
                bundle.putInt("pos", i);
                bundle.putString("name", name.elementAt(i));
                bundle.putInt("pic", picId.elementAt(i));
                bundle.putString("bdDate", bdDate.elementAt(i));
                bundle.putString("home", home.elementAt(i));
                bundle.putString("sex", sex.elementAt(i));
                bundle.putString("nation", nations.elementAt(i));
                bundle.putString("intro", intro.elementAt(i));
                bundle.putString("picPath", picPath.elementAt(i));
                intent.putExtras(bundle);
                startActivityForResult(intent, STARTLIST);
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                final int pos = i;
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("删除" +  name.elementAt(pos) + "?").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        list.remove(pos);
                        name.remove(pos);
                        picId.remove(pos);
                        bdDate.remove(pos);
                        home.remove(pos);
                        sex.remove(pos);
                        nations.remove(pos);
                        intro.remove(pos);
                        picPath.remove(pos);
                        simpleAdapter.notifyDataSetChanged();
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                }).show();
                return true;
            }
        });
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        dealAdapter();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bundle bundle = data.getExtras();
        switch (requestCode){
            case (STARTLIST):
                    int pos = bundle.getInt("pos");
                    picId.setElementAt(bundle.getInt("picId"), pos);
                    name.setElementAt(bundle.getString("name"), pos);
                    sex.setElementAt(bundle.getString("sex"), pos);
                    intro.setElementAt(bundle.getString("intro"), pos);
                    bdDate.setElementAt(bundle.getString("bdDate"), pos);
                    home.setElementAt(bundle.getString("home"), pos);
                    picPath.setElementAt(bundle.getString("picPath"), pos);
                    break;
            case (MAKEADD):
                    picId.add(-1);
                    name.add(bundle.getString("name"));
                    sex.add(bundle.getString("sex"));
                    intro.add(bundle.getString("intro"));
                    bdDate.add(bundle.getString("bdDate"));
                    home.add(bundle.getString("home"));
                    picPath.add(bundle.getString("picPath"));
                    nations.add(bundle.getString("nation"));
                    break;
            case (NOADD):
                    return;
        }
        dealAdapter();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
