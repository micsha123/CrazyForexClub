package com.example.crazyforexclub;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		private int cash = 500;
		private int tax = 25;
		private int buy = 100;
		private int sell = 100;
		private int stock = 25;
		private TextView cashT;
		private TextView stockT;
		private TextView buyT;
		private TextView sellT;
		
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			Button butsel = (Button) rootView.findViewById(R.id.button1);
			Button butbuy = (Button) rootView.findViewById(R.id.button2);
			cashT = (TextView) rootView.findViewById(R.id.textView3);
			stockT = (TextView) rootView.findViewById(R.id.textView8);
			buyT = (TextView) rootView.findViewById(R.id.textView4);
			sellT = (TextView) rootView.findViewById(R.id.textView6);
			cashT.setText(String.valueOf(cash));
			stockT.setText(String.valueOf(stock));
			timeGo();
			butsel.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
						sellShare();
						refreshWin();
				}
			});
			butbuy.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
						buyShare();
						refreshWin();
				}
			});
			return rootView;
		}
		
		private void refreshWin() {
			cashT.setText(String.valueOf(cash));
			stockT.setText(String.valueOf(stock));
		}
		
		private void sellShare() {
			new Thread(new Runnable() {
				@Override
				public void run() {
					getActivity().runOnUiThread(new Runnable() {
						@Override
						public void run() {
							stock -= 1;
							cash += sell;
						}
					});
				}
			}).start();
		}
		private void buyShare() {
			new Thread(new Runnable() {
				@Override
				public void run() {
					getActivity().runOnUiThread(new Runnable() {
						@Override
						public void run() {
							stock += 1;
							cash -= buy;
						}
					});
				}
			}).start();
		}
		private void timeGo() {
			new Thread(new Runnable() {
				@Override
				public void run() {
					while(true){
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					getActivity().runOnUiThread(new Runnable() {
						@Override
						public void run() {
								cash -= tax;
								cashT.setText(String.valueOf(cash));
								buy = (int) (Math.random()*1000);
								sell = (int) (Math.random()*1000);
								buyT.setText(String.valueOf(buy));
								sellT.setText(String.valueOf(sell));
								
						}
					});}
				}
			}).start();
		}
	}

}
