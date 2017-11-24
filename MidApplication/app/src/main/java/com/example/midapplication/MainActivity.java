package com.example.midapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private String[] name = {"刘备","关羽", "周瑜","曹操","诸葛亮","吕布","貂蝉","郭嘉","孙权","夏侯渊"};
    private String[] sex = {"男","男","男","男","男","男","男","男","男","男"};
    private String[] bdDate = {"161-223","?-219","175-210","155-220","181-234","?-198","?-?","170-207","182-252","?-219"};
    private String[] home = {"幽州涿郡涿(河北保定市涿州)","司隶河东郡解(山西运城市临猗县西南)","扬州庐江郡舒(安徽合肥市庐江县西南)","豫州沛国谯(安徽亳州市亳县)","徐州琅邪国阳都(山东临沂市沂南县南)","并州五原郡九原(内蒙古包头市九原区麻池镇西北古城遗址)","暂无相关记载","豫州颍川郡阳翟(河南许昌市禹州)","扬州吴郡富春（浙江杭州市富阳）","豫州沛国谯(安徽亳州市亳县)"};
    private String[] nations = {"蜀","蜀","吴","魏","蜀","东汉","东汉","魏","吴","魏"};
    private String[] intro = {"蜀汉的开国皇帝，相传是汉景帝之子中山靖王刘胜的后代。赤壁之战之际，刘备联吴抗曹，取得胜利，从东吴处“借”到荆州，迅速发展起来，吞并益州，占领汉中，建立蜀汉政权。病逝于白帝城，临终托孤于诸葛亮。",
    "前将军。与张飞追随刘备征战。后因孙权背盟投曹，后方为吕蒙所破，关羽便退兵，但终为孙权军所擒杀。"
            , "自幼与孙策交好，孙策于袁术麾下初崛起时曾随之扫荡江东，为中郎将。孙策遇刺身亡后，周瑜与张昭一起共同辅佐孙权，为中护军，执掌军政大事。周瑜在江陵进行军事准备时死于巴陵，时年三十六岁。孙权曾为其素服吊丧。"
            ,"政治家、军事家、诗人，统一了北方、挟天子以令诸侯，戎马一生。曹操谥武王，曹丕称帝后，追尊他为武皇帝，史称魏武帝。葬于高陵。","政治家、军事家，被誉为“千古良相”的典范。蜀汉建立，拜为丞相。不幸因积劳成疾而逝世，享年五十四岁，谥曰忠武侯。其“鞠躬尽力，死而后已”的高尚品格，千百年来一直为人们所敬仰和怀念。"
            , "长骑射，武力过人，闻名于并州。董卓入京后，诱使吕布杀死丁原，率其众来投。布虽骁猛，然无谋而多猜忌，又信妻言，不纳群下之言。曹操堑围三月，吕布军上下离心，其将侯成、宋宪、魏续缚陈宫，将其众降；吕布亦就缚，与陈宫、高顺被戮于白门楼。"
            ,"舍身报国的可敬女子，她为了挽救天下黎民，为了推翻权臣董卓的荒淫统治，受王允所托，上演了可歌可泣的连环计（连环美人计），周旋于两个男人之间，成功的离间了董卓和吕布，最终吕布将董卓杀死，结束了董卓专权的黑暗时期。"
            ,"曹操之司空军祭酒。因水土不服，气候恶劣，日夜急行操劳过度而病死。曹操深感痛惜，泪流满面。"
            ,"孙权19岁就继承了其兄孙策之位，力据江东，击败了黄祖。后东吴联合刘备，在赤壁大战击溃了曹操军。东吴后来又和曹操军在合肥附近鏖战，并从刘备手中夺回荆州、杀死关羽、大破刘备的讨伐军。曹丕称帝后孙权先向北方称臣，后自己建吴称帝，迁都建业。"
            ,"夏侯婴之后、大将军惇族弟。自曹操陈留起兵起，便跟随征伐，历任陈留、颍川太守。而后夏侯渊督张郃、徐晃等留守汉中，与前来取汉中的刘备大军交战，为刘备将黄忠所袭，不幸战死。谥曰愍侯。魏齐王正始四年从祀曹操庙庭。"};
    private int[] picId = {R.drawable.a, R.drawable.b, R.drawable.c, R.drawable.d, R.drawable.e, R.drawable.f, R.drawable.g, R.drawable.h, R.drawable.i, R.drawable.j};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final List<Map<String, Object>> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Map<String, Object> temp = new LinkedHashMap<>();
            temp.put("itemLetter", (name[i].charAt(0)));
            temp.put("itemName", name[i]);
            list.add(temp);
        }
        final SimpleAdapter simpleAdapter = new SimpleAdapter(this, list, R.layout.item, new String[] {"itemLetter", "itemName"}, new int[] {R.id.itemLetter, R.id.itemName});
        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(simpleAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this, DetailedPage.class);
                Bundle bundle = new Bundle();
                bundle.putString("name", name[i]);
                bundle.putInt("pic", picId[i]);
                bundle.putString("bdDate", bdDate[i]);
                bundle.putString("home", home[i]);
                bundle.putString("sex", sex[i]);
                bundle.putString("nation", nations[i]);
                bundle.putString("intro", intro[i]);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("从购物车删除" +  name[i] + "?").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        list.remove(i);
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
        name.
    }
}
