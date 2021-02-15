(ns pro.juxt.config)

(def conf {:api {:host "http://localhost"
                 :port 8080
                 :auth "Bearer secret"}})

(defn uri []
  (str (-> conf :api :host)
       ":" (-> conf :api :port)
       "/api"))
