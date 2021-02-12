(ns pro.juxt.http.core
  (:require [pro.juxt.config.core :refer [env]]
            [pro.juxt.http.middleware :refer [wrap-middleware]]
            [ring.util.http-response :refer [ok]]
            [mount.core :refer [defstate]]
            [ring.adapter.jetty :refer [run-jetty]]
            [reitit.ring :as ring]))

(defstate handler :start
          (ring/ring-handler
            (ring/router
              [["/api"
                ["/ping" {:get (constantly (ok {:message "pong"}))}]]])))

(defstate http-server
          :start (let [conf (-> env :http)
                       {:keys [port]} conf
                       server (run-jetty (wrap-middleware #'handler) conf)]
                   (println "Server started at port " port)
                   server)
          :stop (.stop http-server))