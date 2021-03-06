
library(usl)

concurrent_users = c(64,128,192,256,320,384,448)

throughput_1n = c(16257,31635,46033,58878,68346,74272,76742)
throughput_2n = c(16283,31788,46156,59385,70845,80283,87009)

data_1n = data.frame(concurrent_users, throughput_1n)
data_2n = data.frame(concurrent_users, throughput_2n)

usl.model_1n <- usl(throughput_1n ~ concurrent_users, data_1n, method = "nlxb")
usl.model_2n <- usl(throughput_2n ~ concurrent_users, data_2n, method = "nlxb")

print("Summary of 1 pod")
print(summary(usl.model_1n))
print("Coefficients 1 pod")
print(coef(usl.model_1n))
print("Peak Users 1 pod")
print(peak.scalability(usl.model_1n))
print("Peak Throughput 1 pod")
print(scalability(usl.model_1n)(peak.scalability(usl.model_1n)))

print("Summary of 2 pods")
print(summary(usl.model_2n))
print("Coefficients 2 pods")
print(coef(usl.model_1n))
print("Peak Users 2 pods")
print(peak.scalability(usl.model_2n))
print("Peak Throughput 2 pods")
print(scalability(usl.model_2n)(peak.scalability(usl.model_2n)))

predict_users = data.frame(concurrent_users = c(64,128,192,256,320,384,448,512,576,640,704,768,832,896,960,1024))
predict_1n = predict(usl.model_1n, predict_users)
predict_2n = predict(usl.model_2n, predict_users)
predict_1n = data.frame(concurrent_users = predict_users, throughput_1n = predict_1n)
predict_2n = data.frame(concurrent_users = predict_users, throughput_1n = predict_2n)

jpeg("ptip-usl.jpg", width = 480, height = 320)
plot(predict_1n, pch=0, ylim=c(0,120000), xlim=c(0,1088), col="gray")
points(data_1n, pch=15, col="blue")

points(predict_2n, pch=1, col="black")
points(data_2n, pch=16, col="green")

legend("bottomright", legend = c("1 pod","2 pods","1 pod predicted","2 pods predicted"), col = c("blue","green","gray","black"),pch=c(15,16,0,1), pt.cex=2, cex=1.2, text.col="black", horiz = F, inset=c(0.1,0.1))
dev.off()
