package com.example.crazyforexclub;

import android.annotation.SuppressLint; 
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
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
	public void onBackPressed() {
	    super.onBackPressed();   
	    stopService(PlaceholderFragment.musich);
		finish();
	}
	private void refreshGame(){
	    Intent intent = getIntent();
	    finish();
	    startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		int id = item.getItemId();
		if (id == R.id.action_settings) {
			refreshGame();
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
		private int tax = 75;
		private int buy = 100;
		private int sell = 100;
		private int stock = 25;
		private TextView cashT;
		private TextView stockT;
		private TextView buyT;
		private TextView sellT;
		private static Intent musich;
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
			musich = new Intent(getActivity(), FoolVilage.class);
			getActivity().startService(musich);
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
		private boolean shouldWork = true;
		private void timeGo() {
			new Thread(new Runnable() {
				@Override
				public void run() {
					while(shouldWork){
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}

					getActivity().runOnUiThread(new Runnable() {
						@SuppressLint("ShowToast")
						@Override
						public void run() {
								cash -= tax;
								cashT.setText(String.valueOf(cash));
								buy = (int) (Math.random()*300);
								sell = (int) (Math.random()*300);
								buyT.setText(String.valueOf(buy));
								sellT.setText(String.valueOf(sell));
								if (cash < 0) {
									shouldWork = false;
									AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
									builder.setTitle(R.string.result)
											.setMessage("You lose your favourite game!")
											.setCancelable(true)
											.setNegativeButton("Ok, gimme one more chance Master!",
													new DialogInterface.OnClickListener() {
														public void onClick(DialogInterface dialog, int id) {
															((MainActivity) getActivity()).refreshGame();
														}
													})
											.setPositiveButton("Go fuck yous*lf!!1", 
													new DialogInterface.OnClickListener() {
														public void onClick(DialogInterface dialog, int id) {
															getActivity().stopService(musich);
															getActivity().finish();
														}
											});
									AlertDialog alert = builder.create();
									alert.show();
								}
						}
					});}
				}
			}).start();
		}
	}

}
