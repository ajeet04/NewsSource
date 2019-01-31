package com.nickelfox.ajeet.newsapp.Activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nickelfox.ajeet.newsapp.R;
import com.squareup.picasso.Picasso;

public class FullArticleActivity extends AppCompatActivity {
    private CollapsingToolbarLayout collapsingToolbar;
    private AppBarLayout appBarLayout;
    private Menu collapsedMenu;
    private boolean appBarExpanded = true;
    String imageurl,url,title,content,discreption,publishedAt,author;
    TextView browse,mContent,mDescp,mPublish,title_;
    ImageView header;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_article);
        imageurl=getIntent().getStringExtra("imageurl");
        title=getIntent().getStringExtra("title");
        content=getIntent().getStringExtra("content");
        discreption=getIntent().getStringExtra("description");
        publishedAt=getIntent().getStringExtra("publishedAt");
        author=getIntent().getStringExtra("author");
        url=getIntent().getStringExtra("url");
        browse=findViewById(R.id.browse);
        title_=findViewById(R.id.title_);
        title_.setText(title);
        mContent=findViewById(R.id.content);
        mContent.setText(content);
        mDescp=findViewById(R.id.descp);
        mDescp.setText(discreption);
        mPublish=findViewById(R.id.publish);
        mPublish.setText(publishedAt);
        header =findViewById(R.id.header);
        if(Build.VERSION.SDK_INT>20) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimary));
        }
        Picasso.get().load(imageurl).fit()
                .centerCrop().placeholder(R.drawable.header).into(header);
        browse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(FullArticleActivity.this,WebViewActivity.class);
                i.putExtra("url",url);
                startActivity(i);
            }
        });


        final Toolbar toolbar = (Toolbar) findViewById(R.id.anim_toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(author);

        appBarLayout = (AppBarLayout) findViewById(R.id.appbar);

        collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(author);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
                R.drawable.header);

        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
            @SuppressWarnings("ResourceType")
            @Override
            public void onGenerated(Palette palette) {
                int vibrantColor = palette.getVibrantColor(R.color.colorPrimary);
                collapsingToolbar.setContentScrimColor(vibrantColor);
                collapsingToolbar.setStatusBarScrimColor(R.color.black_trans80);
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
