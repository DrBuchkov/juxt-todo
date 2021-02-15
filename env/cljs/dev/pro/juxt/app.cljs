(ns pro.juxt.app
  (:require [pro.juxt.core :as core]
            [pro.juxt.config.core :as config]
            [devtools.core :as devtools]
            [re-frisk.core :as re-frisk]
            [day8.re-frame.http-fx]))

(defn reload! []
  (core/mount!))

(re-frisk/enable)

(enable-console-print!)

(devtools/install!)

(core/init!)