## Changes Betwee 1.0.0-alpha3 and 1.0.0-beta1

### HTML Email Templates

`build-email` and `deliver-email` now take an additional
argument, content type, which can be specified as `:text/html`
to generate an HTML email.


### Clojure 1.5 By Default

Postal now depends on `org.clojure/clojure` version `1.5.1`. It is
still compatible with Clojure 1.3 and if your `project.clj` depends on
a different version, it will be used, but 1.5 is the default now.

We encourage all users to upgrade to 1.5, it is a drop-in replacement
for the majority of projects out there.


### Postal Upgrade

Mailer now depends on Postal `1.10.3`.
