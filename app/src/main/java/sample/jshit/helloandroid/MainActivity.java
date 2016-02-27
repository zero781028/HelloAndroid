package sample.jshit.helloandroid;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ListView item_list;
    private TextView show_app_name;

    private ItemAdapter itemAdapter;
    private List<Item> items;

    private MenuItem add_item,search_item,revert_item,delete_item;
    private int selectedCount=0;
    private ItemDAO itemDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        proccessView();
        proccessController();

        itemDAO=new ItemDAO(getApplicationContext());
        if(itemDAO.getCount()==0){
            itemDAO.sample();
        }
        items=itemDAO.getAll();
        itemAdapter=new ItemAdapter(this,R.layout.singleitem,items);
        item_list.setAdapter(itemAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data){
        if(resultCode== Activity.RESULT_OK){
            Item item=(Item)data.getExtras().getSerializable("sample.jshit.helloandroid.Item");

            if(requestCode==0){
                //新增記事資料到資料庫
                item=itemDAO.insert(item);

                items.add(item);
                itemAdapter.notifyDataSetChanged();
            }
            else if(requestCode==1){
                //讀取記事編號
                int position=data.getIntExtra("position",-1);
                if(position!=-1){
                    itemDAO.update(item);
                    //設定修改的記事物件
                    items.set(position,item);
                    itemAdapter.notifyDataSetChanged();
                }
            }
        }
    }

    private void proccessView(){
        item_list=(ListView)findViewById(R.id.item_list);
        show_app_name=(TextView)findViewById(R.id.show_app_name);
    }

    private void proccessController(){

        AdapterView.OnItemClickListener itemListener=new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent,View view,int position,long id){
                Item item=itemAdapter.getItem(position);
                Intent intent=new Intent("sample.jshit.helloandroid.EDIT_ITEM");
                intent.putExtra("position",position);
                intent.putExtra("sample.jshit.helloandroid.Item",item);
                startActivityForResult(intent,1);
            }
        };
        item_list.setOnItemClickListener(itemListener);

        AdapterView.OnItemLongClickListener itemLongClickListener=new AdapterView.OnItemLongClickListener(){
            @Override
            public boolean onItemLongClick(AdapterView<?> parent,View view,int position,long id){
                Item item=itemAdapter.getItem(position);
                proccessMenu(item);
                itemAdapter.set(position,item);
                return true;
            }
        };
        item_list.setOnItemLongClickListener(itemLongClickListener);

        View.OnLongClickListener listener=new View.OnLongClickListener(){

            @Override
            public boolean onLongClick(View view){
                AlertDialog.Builder dialog=new AlertDialog.Builder(MainActivity.this);
                dialog.setTitle(R.string.app_name)
                        .setMessage(R.string.about)
                        .show();
                return false;
            }
        };

        show_app_name.setOnLongClickListener(listener);

    }

    private void proccessMenu(Item item){
        if(item!=null){
            item.setSelected(!item.isSelected());

            if(item.isSelected()){
                selectedCount++;
            }
            else{
                selectedCount--;
            }
        }

        add_item.setVisible(selectedCount==0);
        search_item.setVisible(selectedCount == 0);
        revert_item.setVisible(selectedCount > 0);
        delete_item.setVisible(selectedCount > 0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.menu_main,menu);

        add_item=menu.findItem(R.id.add_item);
        search_item=menu.findItem(R.id.search_item);
        revert_item=menu.findItem(R.id.revert_item);
        delete_item=menu.findItem(R.id.delete_item);
        proccessMenu(null);
        return true;
    }

    public void aboutApp(View view){
        Intent intent=new Intent(this,AboutActivity.class);
        startActivity(intent);
    }

    public void clickMenuItem(MenuItem item){
        int itemId=item.getItemId();

        switch (itemId){
            case R.id.search_item:
                break;
            case R.id.add_item:
                // 使用Action名稱建立啟動另一個Activity元件需要的Intent物件
                Intent intent=new Intent("sample.jshit.helloandroid.ADD_ITEM");
                // 呼叫「startActivityForResult」，，第二個參數「0」表示執行新增
                startActivityForResult(intent,0);
                break;
            case R.id.delete_item:
                if(selectedCount==0){
                    break;
                }

                AlertDialog.Builder d=new AlertDialog.Builder(this);
                String messages=getString(R.string.delete_item);
                d.setTitle(R.string.delete)
                        .setMessage(String.format(messages,selectedCount));
                d.setPositiveButton(android.R.string.yes,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // 刪除所有已勾選的項目
                                int index = itemAdapter.getCount() - 1;

                                while (index > -1) {
                                    Item item = itemAdapter.get(index);

                                    if (item.isSelected()) {
                                        itemAdapter.remove(item);
                                        itemDAO.delete(item);
                                    }

                                    index--;
                                }

                                // 通知資料改變
                                itemAdapter.notifyDataSetChanged();
                                selectedCount = 0;
                                proccessMenu(null);
                            }
                        });
                d.setNegativeButton(android.R.string.no, null);
                d.show();
                break;
            case R.id.revert_item:
                for(int i=0;i<itemAdapter.getCount();i++){
                    Item ri=itemAdapter.getItem(i);

                    if(ri.isSelected()){
                        ri.setSelected(false);
                        itemAdapter.set(i,ri);
                    }
                }

                selectedCount=0;
                proccessMenu(null);
                break;
        }

    }

    public void clickPreferences(MenuItem item){
        startActivity(new Intent(this,PrefActivity.class));
    }
}
