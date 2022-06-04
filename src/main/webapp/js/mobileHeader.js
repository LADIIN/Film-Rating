function showMenu() {
    var x = document.getElementById("myLinks");
    if (x.style.display === "block") {
        x.style.display = "none";
    } else {
        x.style.display = "block";
    }
}

function showContent() {
    var content = document.getElementById("dropdown-content");
    var button = document.getElementById("dropdown-button");
    if (content.style.display === "block") {
        button.style.backgroundColor = "#F73A4C"
        button.style.color = "white";
        content.style.display = "none";
    } else {
        content.style.display = "block";
        button.style.color = "black";
        button.style.backgroundColor = "white"
    }
}