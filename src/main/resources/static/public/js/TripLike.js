async function tripLikeHandler(tId){
    // console.log("안녕");
    let url=`/trip/like/${tId}/handler.do`
    const resp=await fetch(url);
    if(resp.status===200){
        // json : {"handler":1}
        const json=await resp.json();
        if(json.handler>0) { // 등록, 삭제 성공


        }else if(resp.status===400){
            alert("로그인 하셔야 이용 가능한 서비스입니다.")
        }else {
            alert("실패 status : " + resp.status);
        }
    }




}