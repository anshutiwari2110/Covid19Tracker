package adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.palette.graphics.Palette;

import com.anshutiwari.covid19tracker.R;
import com.smarteist.autoimageslider.SliderViewAdapter;

public class SliderAdapter extends SliderViewAdapter<SliderAdapter.SliderHolder> {

    int[] images;
    Context context;
    int[] imgRes = {R.drawable.myth_1,R.drawable.myth_2,R.drawable.myth_3,R.drawable.myth_4,R.drawable.myth_5,R.drawable.myth_6,R.drawable.myth_7,
            R.drawable.myth_8,R.drawable.myth_9,R.drawable.myth_10,R.drawable.myth_11};
    public SliderAdapter(int[] images, Context context) {
        this.images = images;
        this.context = context;
    }
    @Override
    public SliderHolder onCreateViewHolder(ViewGroup parent) {
        return new SliderHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_memories,parent,false));
    }

    @Override
    public void onBindViewHolder(SliderHolder viewHolder, int position) {
        viewHolder.mIvSliderImage.setImageResource(images[position]);
        Bitmap icon = BitmapFactory.decodeResource(context.getResources(),imgRes[position]);
        Palette.from(icon).generate(new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(@Nullable Palette palette) {
                Palette.Swatch swatch = palette.getDominantSwatch();

                viewHolder.mRlSlider.setBackgroundResource(R.drawable.bg_myth);

                GradientDrawable gradientDrawableBg = new GradientDrawable(GradientDrawable.
                        Orientation.BOTTOM_TOP,
                        new int[]{swatch.getRgb(), swatch.getRgb()});

                viewHolder.mRlSlider.setBackground(gradientDrawableBg);
            }
        });
    }

    @Override
    public int getCount() {
        return images.length;
    }

    public static class SliderHolder extends SliderViewAdapter.ViewHolder {
        ImageView mIvSliderImage;
        RelativeLayout mRlSlider;
        public SliderHolder(View itemView) {
            super(itemView);
            mIvSliderImage = itemView.findViewById(R.id.iv_slider_item);
            mRlSlider = itemView.findViewById(R.id.rl_slider);
        }
    }
}
