./scripts/clean.sh
mkdir classes/
clj -M:clj:prod -e "(compile 'pro.juxt.core)"
clj -M:clj:prod:uberdeps --main-class pro.juxt.core