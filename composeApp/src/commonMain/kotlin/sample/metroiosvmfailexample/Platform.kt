package sample.metroiosvmfailexample

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform