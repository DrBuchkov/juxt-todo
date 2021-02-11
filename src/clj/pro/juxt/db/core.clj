(ns pro.juxt.db.core
  (:require [crux.api :as crux]
            [mount.core :refer [defstate]]
            [pro.juxt.config.core :refer [env]])
  (:import (crux.api ICruxAPI)))

(defstate ^ICruxAPI node
          :start (crux/start-node (:crux/config env))
          :close (.close node))
