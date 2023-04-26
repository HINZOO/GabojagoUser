async function removeBtn(cartId){
    let url=`/cart/${cartId}/remove.do`;
    const resp=await fetch(url,{method:"DELETE"});
    if (resp.status===200){
    const result=await resp.json();
        if (json.remove()>0){
            alert("삭제성공")
        }
    }

}
// async function bookBtn(sId,btn){
//     let bi= (btn.classList.contains("bi-bookmark"))
//     console.log(bi)
//     if (bi){
//         let register=await toggleBookMark(sId,btn);
//         if (register==="1"){
//             alert("북마크 성공!");
//             btn.classList.remove("bi-bookmark");
//             btn.classList.add("bi-bookmark-fill")
//         }else {
//             alert("북마크 실패!!")
//         }
//     }else {
//         let remove=await toggleRemoveBook(sId,btn)
//         if(remove==="1"){
//             btn.classList.remove("bi-bookmark-fill")
//             btn.classList.add("bi-bookmark");
//             alert("북마크 취소")
//         }else {
//             alert("북마크 취소 실패하였습니다.")
//         }
//     }
// }
async function bookBtn(sId,btn){
    console.log(sId,btn)
    let url=`/sellBook/${sId}/handler.do`
    const resp=await fetch(url,{method:"POST"});
    if(resp.status===200){
        let status=await resp.text(); //"0" or "1"
        if (status==="1"){
         alert("북마크 되었습니다!!")
        }else {
            alert("북마크 실패했습니다 다시시도하세요")
        }
        return status;
    }else if(resp.status===400){
        alert("로그인 하셔야 이용할 수 있는 서비스 입니다.");
    }else if(resp.status===500){//팔로잉 했는데 한번더 팔로잉하면 db 오류 발생 or db 통신오류
        alert("이미 북마크되었거나 오류가 발생했으니 새로고침하고 다시 시도하세요~");
    }
}
// async function toggleRemoveBook(sId,btn){
//     let url=`/sellBook/${sId}/handler.do`
//     const resp=await fetch(url,{method:"DELETE"});
//     if(resp.status===200){
//         let status =await resp.text();// 0 실패, 1성공
//         return status;
//     }else if(resp.status===400){
//         alert("로그인 하셔야 이용할 수 있는 서비스 입니다.");
//     }else if(resp.status===500){//팔로잉 했는데 한번더 팔로잉하면 db 오류 발생 or db 통신오류
//         alert("오류가 발생했으니 새로고침하고 다시 시도하세요~");
//     }
// }
