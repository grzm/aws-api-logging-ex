(ns com.grzm.ex.pod-aws
  (:require
   [cognitect.aws.client.api :as aws]))

(defn ex [_]
  (let [s3 (aws/client {:api :s3})]
    (prn (keys (aws/invoke s3 {:op :ListBuckets}))))
  (let [sts (aws/client {:api :sts})]
    (prn (keys (aws/invoke sts {:op :GetCallerIdentity})))))
