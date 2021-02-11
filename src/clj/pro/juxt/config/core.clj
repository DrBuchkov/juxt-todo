(ns pro.juxt.config.core
  (:require [config.core :as config]
            [mount.core :refer [defstate]]))

(defstate env :start config/env)