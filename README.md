## PowerText

_Handle TextView with Label and click to show full lines._

[![Download](https://api.bintray.com/packages/lovejjfg/maven/powerText/images/download.svg) ](https://bintray.com/lovejjfg/maven/powerText/_latestVersion)[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-PowerText-brightgreen.svg?style=flat)](https://android-arsenal.com/details/1/6548)

![art3](https://raw.githubusercontent.com/lovejjfg/PowerText/master/art/art3.jpg)![art1](https://raw.githubusercontent.com/lovejjfg/PowerText/master/art/art1.png)

![art2](https://raw.githubusercontent.com/lovejjfg/PowerText/master/art/art2.jpg)

![screenshot](https://raw.githubusercontent.com/lovejjfg/screenshort/0523a2cff6067eeeda05838921d6d13256ffbbcb/WX20171210-205622%402x.png)



## 快速使用

layout:

    <com.lovejjfg.powertext.ExpandableTextView
        android:id="@+id/text"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_margin="20dp"
        android:lineSpacingExtra="10dp"
        android:textColor="#333"
        android:textSize="16sp"
        app:defaultLineCount="3"
        app:defaultMoreHint="查看更多"
        app:labelMargin="5dp"
        app:labelPaddingHorizontal="8dp"
        app:labelPaddingVertical="10dp"
        app:labelStrokeRadius="4dp"
        app:labelTextColor="#333"
        app:labelTextSize="12sp"
        app:moreHintColor="#f00"
        />

    <com.lovejjfg.powertext.LabelTextView
        android:id="@+id/tvLabel"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_gravity="center"
        android:layout_margin="20dp"
        android:lineSpacingExtra="10dp"
        android:textColor="#333"
        android:textSize="16sp"
        app:labelFillColor="true"
        app:labelMargin="10dp"
        app:labelPaddingHorizontal="4dp"
        app:labelPaddingVertical="6dp"
        app:labelStrokeColor="@color/colorAccent"
        app:labelStrokeWidth="1dp"
        app:labelTextColor="#fff"
        app:labelTextSize="12sp"
        />

code:

        text.setLabelText("奶茶妹妹")
        text.setOriginalText(originalText)


## 更新记录

详见 [Release](https://github.com/lovejjfg/PowerText/releases)

