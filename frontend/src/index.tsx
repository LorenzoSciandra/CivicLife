import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import reportWebVitals from './reportWebVitals';
import App from "./App";
import { BrowserRouter } from "react-router-dom";
import MainPage from "./MainPage"
import PersonalData from "./PersonalData";
import Initiatives from "./Initiatives";
import {CardMedia, Grid, Typography} from "@mui/material";
import basquiaPulito from "./imgs/logo_CivicLife.png";
const root = ReactDOM.createRoot(
  document.getElementById('root') as HTMLElement
);

root.render(
  <React.StrictMode>
      <Initiatives/>
      <Grid item display="flex" alignItems={'center'} sx={{width: '100%',
          position: 'fixed',
          margin: "auto",
          bottom:0,
          right: 0}}>
          <CardMedia
              component="img"
              image={basquiaPulito}
              sx={{
                  width: '8%',
              }}
              alt="Logo"
          />
          <Typography
              style={{color: '#feac0d', textAlign: 'center', fontSize: '1.5rem'}}>CivicLife</Typography>
      </Grid>
  </React.StrictMode>
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
