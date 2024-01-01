package cat.jason.koomdemo;

import java.io.File;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.kwai.koom.javaoom.hprof.ForkStripHeapDumper;
import com.kwai.koom.javaoom.monitor.OOMMonitor;
import cat.jason.koomdemo.test.LeakMaker;

public class JavaLeakTestActivity extends AppCompatActivity {

  public static void start(Context context) {
    context.startActivity(new Intent(context, JavaLeakTestActivity.class));
  }

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_java_leak_test);
    Context context = this;
    findViewById(R.id.btn_make_java_leak).setOnClickListener(
            v -> {
              findViewById(R.id.btn_make_java_leak).setVisibility(View.INVISIBLE);
              findViewById(R.id.btn_hprof_dump).setVisibility(View.INVISIBLE);
              findViewById(R.id.tv_make_java_leak_hint).setVisibility(View.VISIBLE);

              /*
               * Init OOMMonitor
               */
              OOMMonitorInitTask.INSTANCE.init(JavaLeakTestActivity.this.getApplication());
              OOMMonitor.INSTANCE.startLoop(true, false, 5_000L);

              /*
               * Make some leaks for test!
               */
              LeakMaker.makeLeak(context);
            }
    );
    String path = this.getFilesDir().getAbsolutePath();
      findViewById(R.id.btn_hprof_dump).setOnClickListener(v -> {
          int[] viewsToHide = {R.id.btn_make_java_leak, R.id.btn_hprof_dump};
          for (int viewId : viewsToHide) {
              findViewById(viewId).setVisibility(View.INVISIBLE);
          }
          findViewById(R.id.tv_hprof_dump_hint).setVisibility(View.VISIBLE);

          ForkStripHeapDumper.getInstance().dump(path + File.separator + "test.hprof");
      });
  }
}
