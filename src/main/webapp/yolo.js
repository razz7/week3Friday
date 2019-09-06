
let url = "http://localhost:8080/jpareststarter/api/movie/all"

function click() {
    fetch(url).then(resposne => response.json())
            .then(data => {
                document.getElementById("text").innerHTML ="<table><th>Film</th><td></td></table>";
    })
}
var btn = document.getElementById("btn");
btn.onclick = click;