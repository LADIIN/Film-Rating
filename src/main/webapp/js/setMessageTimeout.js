window.onload = () => {
    var x = document.getElementById("message");

    x.className = "show";

    setTimeout(function () {
        x.className = x.className.replace("show", "");
    }, 3000);
}
