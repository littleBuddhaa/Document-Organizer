package com.bellatrix.aditi.documentorganizer;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ShareActionProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bellatrix.aditi.documentorganizer.Database.Contract;
import com.bellatrix.aditi.documentorganizer.Database.DBQueries;
import com.bellatrix.aditi.documentorganizer.content.ContentItem;
public class ImageDetailsActivity extends AppCompatActivity {

    private static String folderName;
    private Cursor mCursor;
    private int index;
    // Keep reference to the ShareActionProvider from the menu
    private ShareActionProvider mShareActionProvider;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_details);
        //ImageView ImageView = (ImageView) findViewById(R.id.imageView);
     //   Uri uri =  Uri.parse(getIntent().getExtras().getString("imageUri"));
       // ImageView.setImageURI(uri);
        folderName = getIntent().getStringExtra("folderName");

         index = getIntent().getIntExtra("cIndex",0);

        mCursor = DBQueries.getImageByFolder(this, folderName);
        mCursor.moveToPosition(index);
        Log.d("hh",mCursor.getString(mCursor.getColumnIndex(Contract.Documents.COLUMN_URI)));
//        mItems = getSampleConpositiontent();
//        mCursor.close();
        // Retrieve the ViewPager from the content view
        ViewPager vp = (ViewPager) findViewById(R.id.viewpager);

        // Set an OnPageChangeListener so we are notified when a new item is selected
        vp.addOnPageChangeListener(mOnPageChangeListener);

        // Finally set the adapter so the ViewPager can display items
        vp.setAdapter(mPagerAdapter);
    }





    // BEGIN_INCLUDE(get_sap)
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu resource
        getMenuInflater().inflate(R.menu.main_menu, menu);

        // Retrieve the share menu item
        MenuItem shareItem = menu.findItem(R.id.menu_share);

        // Now get the ShareActionProvider from the item
        mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(shareItem);

        // Get the ViewPager's current item position and set its ShareIntent.
        int currentViewPagerItem = ((ViewPager) findViewById(R.id.viewpager)).getCurrentItem();
        setShareIntent(currentViewPagerItem);

        return super.onCreateOptionsMenu(menu);
    }
    // END_INCLUDE(get_sap)

    /**
     * A PagerAdapter which instantiates views based on the ContentItem's content type.
     */
    private final PagerAdapter mPagerAdapter = new PagerAdapter() {
        LayoutInflater mInflater;


        @Override
        public int getCount() {
            return mCursor.getCount();
        }

        @Override
        public boolean isViewFromObject(View view, Object o) {
            return view == o;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            // Just remove the view from the ViewPager
            container.removeView((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            // Ensure that the LayoutInflater is instantiated
            if (mInflater == null) {
                mInflater = LayoutInflater.from(ImageDetailsActivity.this);
            }
            Log.d("newTag1","hello "+position);
            if(!mCursor.moveToPosition(position))
                return null;
            final ContentItem item=new ContentItem(ContentItem.CONTENT_TYPE_IMAGE, mCursor.getString(mCursor.getColumnIndex(Contract.Documents.COLUMN_URI)));
//            final ContentItem item = i;
            // Get the item for the requested position

          //  String urid = mCursor.getString(mCursor.getColumnIndex(Contract.Documents.COLUMN_URI));

            // The view we need to inflate changes based on the type of content



                    // Inflate item layout for images
            ImageView iv = (ImageView) mInflater
                    .inflate(R.layout.item_image, container, false);

            // Load the image from it's content URI
            iv.setImageURI(item.getContentUri());


            // Add the view to the ViewPager
            container.addView(iv);
                    return iv;


        }
        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }
    };

    private void setShareIntent(int position) {
        // BEGIN_INCLUDE(update_sap)
        if (mShareActionProvider != null) {
            // Get the currently selected item, and retrieve it's share intent
            if(!mCursor.moveToPosition(position))
                return;
            ContentItem item=new ContentItem(ContentItem.CONTENT_TYPE_IMAGE, mCursor.getString(mCursor.getColumnIndex(Contract.Documents.COLUMN_URI)));
            Intent shareIntent = item.getShareIntent(ImageDetailsActivity.this);

            // Now update the ShareActionProvider with the new share intent
            mShareActionProvider.setShareIntent(shareIntent);
        }
        // END_INCLUDE(update_sap)
    }

    /**
     * A OnPageChangeListener used to update the ShareActionProvider's share intent when a new item
     * is selected in the ViewPager.
     */
    private final ViewPager.OnPageChangeListener mOnPageChangeListener
            = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            // NO-OP
        }

        @Override
        public void onPageSelected(int position) {
            setShareIntent(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            // NO-OP
        }
    };

//     ArrayList<ContentItem> getSampleContent() {
//        ArrayList<ContentItem> items = new ArrayList<ContentItem>();
//         mCursor.moveToPosition(0);
//         int ctr = 0;
//        while (!mCursor.isAfterLast()) {
//            ctr+=1;
//            items.add(new ContentItem(ContentItem.CONTENT_TYPE_IMAGE, mCursor.getString(mCursor.getColumnIndex(Contract.Documents.COLUMN_URI))));
//            Log.d("myTag",mCursor.getString(mCursor.getColumnIndex(Contract.Documents.COLUMN_URI)));
//            mCursor.moveToNext();
//        }
//
//        return items;
//    }
    /**
     * @return An ArrayList of ContentItem's to be displayed in this sample
     */


}