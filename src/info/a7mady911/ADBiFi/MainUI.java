package info.a7mady911.ADBiFi;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import com.stericson.RootTools.RootTools;
import com.stericson.RootTools.exceptions.RootDeniedException;
import com.stericson.RootTools.execution.Command;
import com.stericson.RootTools.execution.CommandCapture;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class MainUI extends Activity {

    ImageView iconImageView;
    CommandCapture command;
    String servicePort = "5555";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        iconImageView = (ImageView) findViewById(R.id.iconImageView);
        iconImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (RootTools.isAccessGiven()) {
                    showToast("Root access granted!");
                    command = new CommandCapture(0, "setprop service.adb.tcp.port" + servicePort, "stop adbd", "start adbd");
                    try {
                        RootTools.getShell(true).add(command).waitForFinish();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (TimeoutException e) {
                        e.printStackTrace();
                        showToast("Root access timeout!");
                    } catch (RootDeniedException e) {
                        e.printStackTrace();
                        showToast("Root access denied!");
                    }
                }else{
                    showToast("No root access!");
                }
            }
        });
    }

    //Show Toast message
    private void showToast(String toastText) {
        Toast.makeText(MainUI.this, toastText, Toast.LENGTH_SHORT).show();
    }
}