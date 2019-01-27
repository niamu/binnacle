# Binnacle

Binnacle contains all of your assets within a hash-map in an effort to
make all assets immediately available and minimize requests to the
server.

Texture atlases are nice and also solve this problem, but are
problematic in finding the optimal packing algorithm and make
manipulating a specific image in the pack especially
difficult. Encoding assets as data we pay a small price in increased
payload to the client for the trade-off of being able to manipulate
SVGs within the client without the need to store multiple colour
variants of the same image.

[![binnacle](http://clojars.org/com.niamu/binnacle/latest-version.svg)](https://clojars.org/com.niamu/binnacle)

## Usage

Place all of your assets that you want to package into the binnacle
assets hash-map on your classpath.

Currently supported file types:

- png
- jpg
- gif
- woff

The following example assumes images and fonts were placed on the
classpath at "./resources/assets/". Note how the "./resources/" prefix
is removed from the assets hash-map. This is done to match the paths
when running from within the context of a JAR file.

```Clojure
(ns example.app
  (:require [binnacle.core :as binnacle]))

(def assets (binnacle/assets))
#=> {:assets {:logo.png "BASE64_ENCODED_DATA..."
              :logo.svg [:svg...]}}

(binnacle/data-url assets [:assets :logo.png])
#=> "data:image/png;base64,BASE64_ENCODED_DATA..."

(binnacle/data-url assets [:assets :logo.svg])
#=> "data:image/svg+xml;utf8,%3Csvg%20..."
```

SVG files are represented as Hiccup data structures for easy
manipulation such as changing the fill colour.

```Clojure
(update-in assets [:assets :logo.svg]
           (fn [[tag attrs & children]]
             [tag (assoc attrs :fill "red") children]))
```

## Usage in ClojureScript

With the help of a macro, you can make the asset hash-map available in
ClojureScript as well so you can interactively manipulate SVGs in the
browser.

```Clojure
(ns example.app
  (:require [binnacle.core :as binnacle])
  #?(:cljs (:require-macros
            [example.app :refer [assets*]])))

#?(:clj (defmacro assets* [] (binnacle/assets)))

(def assets (assets*))
```

## License

Copyright © 2017 [Powernoodle](http://powernoodle.com)

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
