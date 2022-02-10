![project_banner](/media/dd_banner.png)

# Directors Digest
A simple Android application showing lists of directors and their films. Data is fetched from a remote source and stored into the local database. If there are any errors in data fetching, default data is shown - default director is Tommy Wiseau and default film is The Room.

## Done with use of:
- [MVVM pattern](https://cdn-blog.scalablepath.com/uploads/2021/12/mvvm-reactive-architecture-1024x937.png)
- Single activity with fragments, navigation is done through [fragment transactions](https://developer.android.com/guide/fragments/transactions)
- [Retrofit](https://square.github.io/retrofit/) for network calls
- [Room](https://developer.android.com/training/data-storage/room) to ease the implementation of local caching
- [ViewBinding](https://developer.android.com/topic/libraries/view-binding) to more easily write code that interacts with views
- [Coroutines](https://developer.android.com/kotlin/coroutines) to avoid using callbacks and do data source calls on background threads
- [LiveData](https://developer.android.com/topic/libraries/architecture/livedata) for notifying observers in the view while being lifecycle aware
- [Dagger Hilt](https://dagger.dev/hilt/) for dependency injection
 
 ## LICENSE
```
MIT License

Copyright (c) 2022 Petar Slavkovic

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```
