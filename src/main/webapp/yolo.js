
let url = "https://rhemmingsen.com/rest-jpa-devops-starter/api/movie/all"

function click() {
    fetch(url).then(res => res.json())
            .then(data => {
                var movies = data.map(movies => "<tr><td>" + movies.name + "</td>" +
                "<td>" + movies.rating + "</td>" +
                "<td>" + movies.year + "</td></tr>");
                console.log("clicked");
                var movList = movies.join("\n");
                document.getElementById("tableB").innerHTML = movList;
    })
}
var btn = document.getElementById("btn");
btn.onclick = click;