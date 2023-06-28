
package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Fragment.BlankFragment;
import com.example.myapplication.utils.Constant;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

public class AdminDashBoardActivity extends AppCompatActivity {
    private static final int REQUEST_CODE1 = 1;
    private static final int SELECT_PHOTO = 100;

    private static final int STORAGE_PERMISSION = 111;
    FloatingActionButton floatingAdd;
    RecyclerView saloon1;


    //  String saloonId = "";
    private SearchView searchView;
    Activity mactivity;
    double count;
    Double value;
    Double avgrat;
    String saloonId = "";

    Toolbar toolbar; //su141
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    BottomNavigationView bottomNavigationView; //sua141
    private static final int FRAGMENT_HOME = 0;
    private static final int FRAGMENT_Appoitment = 1;
    private static final int FRAGMENT_profile = 2;
    private static final int FRAGMENT_Customer = 3;

    private int mCurrentFragment = FRAGMENT_HOME;
    public static final int MY_REQUEST_CODE = 10;

    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dash_board);
        Toast.makeText(getApplicationContext(),"ở đây",Toast.LENGTH_LONG).show();
        getIntentData(getIntent()); //sua
        position = getIntent().getIntExtra("position",-2);
//         getInit();
//          setEvents();
//          getlistsaloon();
        replaceFragment(new BlankFragment());
        bottomNavigationView = findViewById(R.id.navigation_view);
        bottomNavigationView.setVisibility(View.VISIBLE);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.nav_home) {
                    if (mCurrentFragment != FRAGMENT_HOME) {
                        //   startActivity(getIntent());
                        replaceFragment(new BlankFragment());
                        mCurrentFragment = FRAGMENT_HOME;
                    }
                } else if (id == R.id.nav_profile) {
                    if (mCurrentFragment != FRAGMENT_profile) {
                        replaceFragment(new Customer_Fragment() );
                        mCurrentFragment = FRAGMENT_profile;
                    }
                }
//                mDrawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });


    }
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == REQUEST_CODE1 && resultCode == Activity.RESULT_OK) {
//            super.onRestart();
//        }
//    }

    private void replaceFragment(Fragment frag) { //chuyển màn hình
//        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//        transaction.replace(R.id.rl1,frag);
//        transaction.commit();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.rl1, frag).addToBackStack(null).commit();
    }

//    @Override
//    protected void onRestart() {
//        super.onRestart();
//    }

    @Override
    protected void onPause() {
        super.onPause();

        // Toast.makeText(this, "da dung", Toast.LENGTH_SHORT).show();
    }
//



//    private void setEvents() {
//        floatingAdd.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(AdminDashBoardActivity.this, AddSaloonActivity.class);
//                startActivity(intent);
//            }
//        });
//    }

//    private void getInit() {
//        floatingAdd = findViewById(R.id.floatingAdd);
//        saloon1 = findViewById(R.id.adminpanelmonitor);
//
//        saloon1.setLayoutManager(new LinearLayoutManager(this));
//        mlistSaloon = new ArrayList<>();
//        mactivity = AdminDashBoardActivity.this;
//
//
//    }
    @Override
    protected void onRestart() {
        super.onRestart();
    }

    String idUser;

    public void getIntentData(Intent intent) { //sua
        saloonId = intent.getStringExtra(Constant.ARGS_SALOON);
        //   idUser = intent.getStringExtra(Constant.ARGS_USER);
    } //sua

//    @Override
//    public void onBackPressed() {
//        AlertDialog.Builder builder = new AlertDialog.Builder(AdminDashBoardActivity.this);
//        builder.setTitle("Are you sure?");
//        builder.setMessage("Bạn có muốn thoát thật không ?");
//        builder.setPositiveButton("Exit", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//
//                Intent intent1 = new Intent(AdminDashBoardActivity.this, MainActivity.class);
//                intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                startActivity(intent1);
//            }
//        });
//        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                Toast.makeText(AdminDashBoardActivity.this, "Cancelled", Toast.LENGTH_SHORT).show();
//            }
//        });
//        builder.show();
//    }
    public String  getiddata(){
        String uid;
        uid=saloonId;
        return uid;
    }
//    @Override
//    public void onResume() {
//        super.onResume();
//        floatingAdd.setVisibility(View.GONE);
//    }


}