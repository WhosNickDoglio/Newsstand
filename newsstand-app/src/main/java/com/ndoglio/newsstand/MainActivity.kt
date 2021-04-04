/*
 * MIT License
 *
 *   Copyright (c) 2021 Nicholas Doglio
 *
 *   Permission is hereby granted, free of charge, to any person obtaining a copy
 *   of this software and associated documentation files (the "Software"), to deal
 *   in the Software without restriction, including without limitation the rights
 *   to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *   copies of the Software, and to permit persons to whom the Software is
 *   furnished to do so, subject to the following conditions:
 *
 *   The above copyright notice and this permission notice shall be included in all
 *   copies or substantial portions of the Software.
 *
 *   THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *   IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *   FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *   AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *   LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *   OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *   SOFTWARE.
 */

package com.ndoglio.newsstand

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.ndoglio.feedly.ui.HomeLayoutRunner
import com.ndoglio.feedly.ui.login.LoginLayoutRunner
import com.ndoglio.newsstand.di.injector
import com.ndoglio.onboarding.OnboardingLayoutRunner
import com.squareup.workflow1.ui.ViewRegistry
import com.squareup.workflow1.ui.WorkflowLayout
import com.squareup.workflow1.ui.backstack.BackStackContainer
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var appWorkflowViewModelFactory: AppWorkflowViewModel.Factory

    //TODO make this better
    private val model by viewModels<AppWorkflowViewModel> {
        createAbstractSavedStateViewModelFactory(
            intent.extras ?: Bundle()
        ) { appWorkflowViewModelFactory.create(it) }
    }

    // TODO solve this with dagger?
    private val viewRegistry = ViewRegistry(
        BackStackContainer,
        OnboardingLayoutRunner,
        LoginLayoutRunner,
        HomeLayoutRunner
    )

    // TODO think about a MainViewModel or AuthenticationViewModel that will just observe User ID and if it's ever null it will show login flow
    override fun onCreate(savedInstanceState: Bundle?) {
        injector.inject(this)
        super.onCreate(savedInstanceState)
        intent?.pushIntent()
        setContentView(WorkflowLayout(this).apply { start(model.renderings, viewRegistry) })
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        intent?.pushIntent()
    }

    private fun Intent?.pushIntent() {
        if (this != null) model.props.value = AppWorkflow.Props(this, this@MainActivity)
    }
}
