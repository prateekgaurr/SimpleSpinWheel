# Simple Spin Wheel for Android

Simple Spin Wheel is a Custom View for Android.
- Can be supplied with an array of strings to be displayed.
- Have a selector arrow in the right side of the wheel.
- Section Colors can be modified.
- Can be spinned by calling just one function.
- You can set listeners to get the spin result string OnClick

## Screenshots
![Simple Spin Wheel By Prateek Gaur prateekgaurr](https://github.com/prateekgaurr/SimpleSpinWheel/blob/master/screenshots/ss1.png?raw=true?raw=true "Simple Spin Wheel View")

## Installation

Use gradle to install Simple Spin Wheel

```
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}

```

If you are using Kotlin DSL for grade build. (build.gradle.kts)
In your settings.gradle.kts file, add jitpack.io in Repositories
```
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        ...
        maven(java.net.URI.create("https://jitpack.io"))
    }
}
```

And the dependency in your app module grade file
```
dependencies {

            //for simple spin wheel 
	        implementation 'com.github.prateekgaurr:SimpleSpinWheel:1.0.0'

	}
```


## Usage
This is how you can use Simple Spin Wheel


In your layout file
```xml
<com.prateek.simplespinwheel.SimpleSpinWheel
        android:id="@+id/<ID_OF_YOUR_CHOICE>"
        android:layout_width="<WIDTH_OF_YOUR_CHOICE>"
        android:layout_height="<HEIGHT_OF_YOUR_CHOICE>" />
```

In Your Kotlin File


1. Implement the interfaces
```kotlin
class MainActivity : AppCompatActivity(), OnSpinCompleteListener, OnClickListener
```

2. Declare the Wheel
```kotlin
private lateinit var wheel : SimpleSpinWheel
```

3. Initialize the Wheel
```kotlin
wheel = binding.simpleSpinWheel
OR
wheel = findViewById<SimpleSpinWheel>(R.id.XXXXXX)
```

4. Set the Listeners
```kotlin
wheel.setOnSpinCompleteListener(this)
wheel.setOnClickListener(this)
```

5. Set the Items to to divided and shown in sections
- Input Type - Array<String>
```kotlin
wheel.setItems(arrayOf(
      "Item1", "Item2", "Item3", "Item4", "Item5", "Item6")
)
```


6. Set the Colors of sections
- If no. of items is greater than the colours passed, the colours will be repeated automatically
-Input Type - Array<R.color.XXXXX>

```kotlin
wheel.setColors(arrayOf(
                android.R.color.holo_red_light,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_blue_light)
)
```

7. Override the functions from the interfaces to handle the result and click
```kotlin
override fun onSpinComplete(selectedItem: String) {
       //do something with the resulted item
}


override fun onClick(p0: View?) {
       //do something when clicked on Spin Wheel
}

```


## Contributing
Contributions are welcome!
