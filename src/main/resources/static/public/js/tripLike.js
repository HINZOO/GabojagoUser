function loginAlert(){
    alert("로그인 하셔야 이용 가능한 서비스 입니다.");
}

async function tripLikeHandler(tId) {
    const likeCont = document.getElementById("likeCont"+tId);
    // if(tId===null) tId=0;
    console.log(tId)
    let url = `/trip/like/${tId}/handler.do`
    const resp = await fetch(url);
    if (resp.status === 200) {
        // json : {"handler":1}
        const json = await resp.json();
        if (json.handler > 0) { // 등록, 삭제 성공

            let html = await readLike(tId); // 해당 게시글에 대한 좋아요 개수
            if (html) {
                likeCont.innerHTML = html;
            }
        }

    } else if (resp.status === 400) {
        alert("잠시후 다시 시도해주세요.");
    } else { // 실패
        alert("실패 status:" + resp.status);
    }

    async function readLike(tId) {
        let url = `/trip/like/${tId}/read.do`;
        console.log(tId)
        const resp = await fetch(url);
        if (resp.status === 200) {
            const html = await resp.text();
            console.log(html);
            return html;
        }
    }
}