(ns pro.juxt.config.core
  (:require [mount.core :refer [defstate]])
  #?(:clj  (:require [config.core :as config])
     :cljs (:require [pro.juxt.config :refer [conf]])))

(defstate env :start #?(:clj  config/env
                        :cljs conf))

