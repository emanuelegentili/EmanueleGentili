package mypackage.workspace;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class DragViewActivity extends Activity {
    /** Called when the activity is first created. */
    private View selected_item=null;
    private int offset_x=0;
    private int offset_y=0;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
 
///PRIMA IMMAGINE
        ImageView iv = (ImageView) findViewById(R.id.imageView1);
        iv.setOnTouchListener(new View.OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if(event.getActionMasked()==MotionEvent.ACTION_DOWN){
					offset_x=(int) event.getX();
					offset_y=(int) event.getY();
					selected_item = v;
				}
				
				return false;
			}
		});
        
///SECONDA IMMAGINE
        ImageView iv2 = (ImageView) findViewById(R.id.imageView2);
        iv2.setOnTouchListener(new View.OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if(event.getActionMasked()==MotionEvent.ACTION_DOWN){
					offset_x=(int) event.getX();
					offset_y=(int) event.getY();
					selected_item = v;
				}
				
				return false;
			}
		});        
        
        
        RelativeLayout rl =(RelativeLayout) findViewById(R.id.relativeLayout1);
        rl.setOnTouchListener(new View.OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if(selected_item==null) return false;
				if(event.equals(MotionEvent.ACTION_MOVE)){
					
					int x = (int) event.getX() - offset_x;
					int y = (int) event.getY() - offset_y;
					
					int w = getWindowManager().getDefaultDisplay().getWidth()-128;
					int h = getWindowManager().getDefaultDisplay().getHeight()-128;
					if(x>w)
						x=w;
					if(y>h)
						y=h;
					
					RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams 
							(new ViewGroup.MarginLayoutParams
									(RelativeLayout.LayoutParams.WRAP_CONTENT, 
											RelativeLayout.LayoutParams.WRAP_CONTENT));
			        lp.setMargins(x, y, 0, 0);
			        selected_item.setLayoutParams(lp);
			    }
				if (event.getActionMasked() == MotionEvent.ACTION_UP) {
					selected_item = null;
				}
				return true;
			}
		});
        
    }
}