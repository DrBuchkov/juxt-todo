(ns pro.juxt.http.core
  (:require [pro.juxt.config.core :refer [env]]
            [pro.juxt.http.middleware :refer [wrap-middleware]]
            [pro.juxt.http.todo :refer [todo-routes]]
            [ring.util.http-response :refer [ok]]
            [mount.core :refer [defstate]]
            [ring.adapter.jetty :refer [run-jetty]]
            [reitit.ring :as ring]))

(defstate handler :start
          (ring/ring-handler
            (ring/router
              [["/api"
                ["/ping" {:get (constantly (ok {:message "pong"}))}]
                (todo-routes)]])))

(defstate http-server
          :start (let [conf (-> env :http)]
                   (run-jetty (wrap-middleware #'handler) conf))
          :stop (.stop http-server))
