package ua.kobyakov.offerwall.activity;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.kobyakov.unity.UnityPlayerActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ua.kobyakov.offerwall.R;
import ua.kobyakov.offerwall.viewmodel.StartActivityVM;

public class StartActivity extends AppCompatActivity {
    private static final String TAG = StartActivity.class.getSimpleName();
    private static final int LAYOUT = R.layout.activity_start;
    private StartActivityVM viewModel;

    @BindView(R.id.block_error)
    LinearLayout blockError;
    @BindView(R.id.network_error)
    TextView networkError;
    @BindView(R.id.no_internet)
    TextView noInternet;
    @BindView(R.id.btn_refresh)
    TextView btnRefresh;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(LAYOUT);

        ButterKnife.bind(this);

        viewModel = ViewModelProviders.of(this).get(StartActivityVM.class);

        viewModel.getResponseWithDB().observe(this, response -> {
            if (response != null) {
                if (response.isAllow()) {
                    loadGame();
                } else {
                    loadWebView();
                }
            } else {
                Toast.makeText(getApplicationContext(), R.string.network_request, Toast.LENGTH_SHORT).show();
                viewModel.sendRequest();
            }
        });

        viewModel.getStatusCode().observe(this, code -> {
            if (code != null && code != 0) {
                progressBar.setVisibility(View.GONE);
                blockError.setVisibility(View.VISIBLE);
                if (code == -200) {
                    noInternet.setVisibility(View.VISIBLE);
                } else {
                    networkError.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @OnClick(R.id.btn_refresh)
    public void refresh() {
        viewModel.sendRequest();
        blockError.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        Toast.makeText(getApplicationContext(), R.string.network_request, Toast.LENGTH_SHORT).show();
    }

    public void loadWebView() {
        Log.d(TAG, "loadWebView");
        Intent intent = new Intent(this, WebViewActivity.class);
        startActivity(intent);
        finish();
    }

    public void loadGame() {
        Log.d(TAG, "loadGame");
        Intent intent = new Intent(this, UnityPlayerActivity.class);
        startActivity(intent);
        finish();
    }
}