import "./App.css";
import React from "react";
import { Button, TextField } from "@mui/material";

function App() {
  window.oncontextmenu = function (event) {
    event.preventDefault();
    event.stopPropagation();
    return false;
  };

  const [search, changeSearch] = React.useState("");
  const [v, cValue] = React.useState("");
  const [i, cImg] = React.useState("");

  return (
    <div className="App">
      <div className="NavBar">
        <img
          src={`${process.env.PUBLIC_URL}/images/fumo.png`}
          className="fumo"
          alt="fumo"
        ></img>
        <TextField
          variant="filled"
          className="SearchBar"
          label="동캐 이름 입력 (ex: 하쿠레이레이무)"
          onChange={(e) => {
            changeSearch(e.target.value);
          }}
          onKeyDown={(key) => {
            if (key.key === "Enter") {
              fetch("http://localhost:8088/specific", {
                method: "POST",
                headers: {
                  "Content-Type": "application/json",
                },
                body: JSON.stringify({
                  in: search,
                }),
              })
                .then((res) => res.json())
                .then((res) => {
                  cValue(res.out);
                  fetch("http://localhost:8088/img", {
                    method: "POST",
                    headers: {
                      "Content-Type": "application/json",
                    },
                    body: JSON.stringify({
                      in: res.out,
                    }),
                  })
                    .then((r) => r.json())
                    .then((r) => {
                      cImg(r.out);
                    });
                });
            }
          }}
        ></TextField>
        <Button
          variant="contained"
          className="Random"
          color="primary"
          onClick={() => {
            fetch("http://localhost:8088/random", {
              method: "POST",
              headers: {
                "Content-Type": "application/json",
              },
            })
              .then((res) => res.json())
              .then((res) => {
                cValue(res.out);
                fetch("http://localhost:8088/img", {
                  method: "POST",
                  headers: {
                    "Content-Type": "application/json",
                  },
                  body: JSON.stringify({
                    in: res.out,
                  }),
                })
                  .then((r) => r.json())
                  .then((r) => {
                    cImg(r.out);
                  });
              });
          }}
        >
          랜덤 동캐
        </Button>
      </div>
      <div className="Main">
        <img src={i} className={`pfImage`} alt=""></img>
        <a href={v}>{search}</a>
      </div>
    </div>
  );
}

export default App;
