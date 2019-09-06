
let url = "https://rhemmingsen.com/rest-jpa-devops-starter/api/movie/all"

function click() {
    fetch(url).then(resposne => response.json())
            .then(data => {
                document.getElementById("text").innerHTML ="<table><th>Film</th><td></td></table>";
    })
}
var btn = document.getElementById("btn");
btn.onclick = click;