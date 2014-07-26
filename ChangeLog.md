## Changes Between 1.0.0 and 1.1.0

### Support for alternative email bodies

`build-email` and `deliver-email` now take extra set of template,
data, content-type for alternative email body. This is useful for
supplying alternative plain-text body in addition to main HTML
body of the message.

### Clojure 1.6 By Default

The project now depends on `org.clojure/clojure` version `1.6.0`. It is
still compatible with Clojure 1.4 and if your `project.clj` depends on
a different version, it will be used, but 1.6 is the default now.

We encourage all users to upgrade to 1.6, it is a drop-in replacement
for the majority of projects out there.

### Content Type as a String

Content type now can be provided as a string as well as a (predefined) keyword,
e.g.

``` clojure
(build-email {} "templates/hello.mustache" {} "text/html")
```

Contributed by bpr.



## Changes Between 1.0.0-alpha3 and 1.0.0

### HTML Email Templates

`build-email` and `deliver-email` now take an additional
argument, content type, which can be specified as `:text/html`
to generate an HTML email.


### Clojure 1.5 By Default

Mailer now depends on `org.clojure/clojure` version `1.5.1`. It is
still compatible with Clojure 1.3 and if your `project.clj` depends on
a different version, it will be used, but 1.5 is the default now.

We encourage all users to upgrade to 1.5, it is a drop-in replacement
for the majority of projects out there.


### Postal Upgrade

Mailer now depends on Postal `1.11.1`.


### Clostache Upgrade

Mailer now depends on Clostache `1.3.1`.
