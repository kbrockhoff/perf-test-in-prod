
library(usl)

concurrent_users = c(100,200,500,1000)

throughput_500b = c(17588.2,19509.07,19940.62,19764.01)
throughput_1k = c(16667.76,18175.66,18235.69,18255.44)
throughput_10k = c(13173.19,13937.52,13434.7,13203.39)

specsdm91_500b = data.frame(concurrent_users, throughput_500b)
specsdm91_1k = data.frame(concurrent_users, throughput_1k)
specsdm91_10k = data.frame(concurrent_users, throughput_10k)

usl.model_500b <- usl(throughput_500b ~ concurrent_users, specsdm91_500b, method = "nlxb")
usl.model_1k <- usl(throughput_1k ~ concurrent_users, specsdm91_1k, method = "nlxb")
usl.model_10k <- usl(throughput_10k ~ concurrent_users, specsdm91_10k, method = "nlxb")

print("Summary of 500b")
print(summary(usl.model_500b))
print("Peak Users 500b")
print(peak.scalability(usl.model_500b))
print("Peak Throughput 500b")
print(scalability(usl.model_500b)(peak.scalability(usl.model_500b)))

print("Summary of 1k")
print(summary(usl.model_1k))
print("Peak Users 1k")
print(peak.scalability(usl.model_1k))
print("Peak Throughput 1k")
print(scalability(usl.model_1k)(peak.scalability(usl.model_1k)))

print("Summary of 10k")
print(summary(usl.model_10k))
print("Peak Users 10k")
print(peak.scalability(usl.model_10k))
print("Peak Throughput 10k")
print(scalability(usl.model_1k)(peak.scalability(usl.model_10k)))

jpeg("rplot.jpg", width = 350, height = 350)
plot(specsdm91_500b, pch=15, ylim=c(0,max(unlist(throughput_500b))+200), col="black")
plot(usl.model_500b, add = TRUE, col="black")

points(specsdm91_1k, pch=16, ylim=c(0,max(unlist(throughput_1k))+200), col="blue")
plot(usl.model_1k, add = TRUE, col="blue")

points(specsdm91_10k, pch=17, ylim=c(0,max(unlist(throughput_10k))+200), col="red")
plot(usl.model_10k, add = TRUE, col="red")

legend("bottomleft", legend = c("500B","1KB","10KB"), col = c("black","blue","red"),pch=c(15,16,17), pt.cex=2, cex=1.2, text.col="black", horiz = F, inset=c(0.1,0.1,0.1))
dev.off()
