================
ZMON Android App
================

.. image:: https://travis-ci.org/zalando/zmon-android.svg?branch=master
   :target: https://travis-ci.org/zalando/zmon-android
   :alt: Build Status

This is the Android app for `ZMON, Zalando's open-source platform monitoring tool <http://zalando.github.io/zmon/>`_.

Our `ZMON Documentation <http://zmon.readthedocs.org/>`_ provides more information.

Building
========

Download Android SDK from https://developer.android.com/sdk/.

.. code-block:: bash

    $ ./gradlew assemble
    $ # connect your Android device (needs to have USB debugging enabled)
    $ adb install app/build/outputs/apk/app-debug.apk

Configuration
=============
In order to use the feature to receive notifications for upcoming alerts via Google Cloud Messaging (GCM) Service, you need
to configure those two string values in `google_api.xml`:

.. code-block:: xml

    <string name="google_api_key">YOUR_GOOGLE_API_KEY</string>
    <string name="gcm_sender_id">YOUR_GCM_SENDER_ID</string>

License
=======

Copyright 2013-2015 Zalando SE

Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
