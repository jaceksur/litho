/*
 * Copyright (c) Meta Platforms, Inc. and affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.facebook.litho.widget;

import android.content.Context;
import android.view.View;
import androidx.annotation.UiThread;
import com.facebook.litho.ComponentContext;
import com.facebook.litho.ComponentLayout;
import com.facebook.litho.RenderAndMeasureCounter;
import com.facebook.litho.Size;
import com.facebook.litho.SizeSpec;
import com.facebook.litho.annotations.MountSpec;
import com.facebook.litho.annotations.OnCreateMountContent;
import com.facebook.litho.annotations.OnMeasure;
import com.facebook.litho.annotations.OnPrepare;
import com.facebook.litho.annotations.Prop;
import com.facebook.litho.annotations.ShouldUpdate;

@MountSpec(isPureRender = true)
public class RenderAndLayoutCountingTesterSpec {

  @OnPrepare
  static void onPrepare(
      ComponentContext c,
      @Prop RenderAndMeasureCounter renderAndMeasureCounter,
      @Prop(optional = true) Listener listener) {

    if (listener != null) {
      listener.onPrepare();
    }

    renderAndMeasureCounter.incrementRenderCount();
  }

  @OnMeasure
  static void onMeasure(
      ComponentContext c,
      ComponentLayout layout,
      int widthSpec,
      int heightSpec,
      Size size,
      @Prop RenderAndMeasureCounter renderAndMeasureCounter,
      @Prop(optional = true) Listener listener) {

    if (listener != null) {
      listener.onMeasure();
    }

    size.width = SizeSpec.getSize(widthSpec);
    size.height = SizeSpec.getSize(heightSpec);

    renderAndMeasureCounter.incrementMeasureCount();
  }

  @UiThread
  @OnCreateMountContent
  static View onCreateMountContent(Context c) {
    return new View(c);
  }

  @ShouldUpdate
  static boolean shouldUpdate() {
    return true;
  }

  public static interface Listener {
    void onPrepare();

    void onMeasure();
  }
}
