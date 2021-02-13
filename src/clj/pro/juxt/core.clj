(ns pro.juxt.core
  (:require [mount.core :refer [defstate] :as mount]
            [clojure.tools.logging :as log])
  (:gen-class))


(defn start-app! []
  (doseq [component (-> (mount/start) :started)]
    (log/info component "started")))

(defn stop-app! []
  (doseq [component (-> (mount/stop)
                        :stopped)]
    (log/info component "stopped")))

(defn -main [& args]
  (println "Hello World!")
  (.addShutdownHook (Runtime/getRuntime) (Thread. ^Runnable (fn []))))