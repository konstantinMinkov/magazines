/**
 * Created by Konstantin Minkov on 07.07.2016.
 */


function onSliderInput() {
    var num = document.getElementById("editions-slider").value;
    document.getElementById("editions-field").value = num;
    changePriceAndIssues(num);
}

function onIssuesInput() {
    var num = document.getElementById("editions-field").value;
    if (num > 12) {
        num = 12;
    }
    if (num < 1) {
        num = 1;
    }
    if ((num % 1) > 0) {
        num = parseInt(num);
    }
    document.getElementById("editions-field").value = num;
    document.getElementById("editions-slider").value = num;
    changePriceAndIssues(num);
}

function changePriceAndIssues(num) {
    var price = document.getElementById("issue-cost").textContent;
    document.getElementById("total-cost").textContent = (price * num).toFixed(2);
    document.getElementById("total-issues").textContent = num;
}

function loadImage(event) {
    var files = event.target.files;
    var f = files[0];
    var reader = new FileReader();

    reader.onload = (function(theFile) {
        return function(e) {
            var img = document.getElementById("editionImage");
            if (img === null || img === undefined) {
                var emptyBlock = document.getElementById("imgPlaceholder");
                img = document.createElement("img");
                document.getElementById("imageContainer").replaceChild(img, emptyBlock);
                img.setAttribute("class", "img-responsive");
            }
            img.setAttribute("src", e.target.result);
        };
    })(f);

    reader.readAsDataURL(f);
}