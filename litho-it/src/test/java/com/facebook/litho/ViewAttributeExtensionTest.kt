/*
 * Copyright (c) Facebook, Inc. and its affiliates.
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

package com.facebook.litho

import android.view.View
import com.facebook.litho.testing.LithoViewRule
import com.facebook.litho.testing.testrunner.LithoTestRunner
import com.facebook.litho.widget.MountSpecLifecycleTester
import org.assertj.core.api.Java6Assertions.assertThat
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(LithoTestRunner::class)
class ViewAttributeExtensionTest {

  @JvmField @Rule var lithoViewRule: LithoViewRule = LithoViewRule()

  @Test
  fun `when view attributes is set it should override attribute set by mount spec`() {

    val c: ComponentContext = lithoViewRule.context

    val root: Component =
        MountSpecLifecycleTester.create(c)
            .intrinsicSize(Size(100, 100))
            .lifecycleTracker(LifecycleTracker())
            .defaultAlpha(0.5f)
            .alpha(0.2f)
            .build()

    lithoViewRule.render { root }

    val content: View = lithoViewRule.lithoView.getChildAt(0)

    assertThat(content.alpha)
        .describedAs("alpha should be applied from the common props")
        .isEqualTo(0.2f)

    // unmount everything
    lithoViewRule.useComponentTree(null)

    assertThat(content.alpha)
        .describedAs("alpha should be restored to the initial value")
        .isEqualTo(0.5f)
  }
}
