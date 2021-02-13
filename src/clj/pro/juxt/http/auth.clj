(ns pro.juxt.http.auth
  (:require [pro.juxt.config.core :refer [env]]
            [juxt.reap.alpha.regex :as re]
            [juxt.reap.alpha.decoders.rfc7235 :refer [authorization]]))

(defn wrap-authorization [handler]
  (fn [req]
    (let [authorization-headers (-> req
                                    :headers
                                    (get "authorization"))
          auth-decoder (authorization {})
          {:juxt.reap.alpha.rfc7235/keys [auth-scheme token68]} (-> authorization-headers
                                                                    re/input
                                                                    auth-decoder)]
      (when-not (and (= auth-scheme (-> env :http :auth-scheme))
                     (= token68 (-> env :http :auth-token)))
        (throw (ex-info "Unauthorized" {:pro.juxt/error-type :pro.juxt/unauthorized
                                        :pro.juxt/error-data {:scheme auth-scheme
                                                              :token  token68}})))
      (handler req))))
