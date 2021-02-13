(ns pro.juxt.http.todo
  (:require [ring.util.http-response :refer [ok]]
            [clojure.tools.logging :as log]))


(defn todo-routes []
  ["/todo" {:get (fn [req]
                   (ok {:todo {}}))}])
