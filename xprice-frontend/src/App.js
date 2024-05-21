import React, { useState, useEffect } from 'react';
import axios from 'axios';
import Box from '@mui/material/Box';
import InputLabel from '@mui/material/InputLabel';
import MenuItem from '@mui/material/MenuItem';
import Button from '@mui/material/Button';
import FormControl from '@mui/material/FormControl';
import Grid from '@mui/material/Grid';
import Select, { SelectChangeEvent } from '@mui/material/Select';
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import Paper from '@mui/material/Paper';
import TablePagination from '@mui/material/TablePagination';


function App() {
    const [products, setProducts] = useState([]);
    const [selectedProduct, setSelectedProduct] = useState();
    const [productPrices, setProductPrices] = useState({
            totalCount: 0,
            prices: [],
            page: 0,
            lastUpdatedTime: 0
    });
    const [page, setPage] = React.useState(0);

    useEffect(() => {
            if (selectedProduct) {
                handleGetPrices();
            }
        }, [page]);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await axios.get('http://localhost:8080/products');
        setProducts(response.data);
      } catch (error) {
        console.error('Error fetching data:', error);
      }
    };
    fetchData();
  }, []);

   const handleChange = (event: SelectChangeEvent) => {
      setSelectedProduct(event.target.value);
   };

    const handleGetPrices = async () => {
         try {
            console.log(selectedProduct.id)
            const response = await axios.get(`http://localhost:8080/prices/${selectedProduct}/${page}`);
            setProductPrices(response.data);
         } catch (error) {
            console.error('Error fetching data:', error);
         }
    };

    const handleRefresh = async () => {
         try {
             const response = await axios.post(
                 'http://localhost:8080/prices/refresh',
                 { productId: selectedProduct }, // Gönderilecek JSON verisi
                 {
                     headers: {
                         'Content-Type': 'application/json'
                     }
                 }
             );
             setProductPrices(response.data);
             setPage(0);
         } catch (error) {
             console.error('Error refreshing prices:', error);
         }
    };


     const handleChangePage = (event: unknown, newPage: number) => {
        setPage(newPage);
      };

  return (
     <Box sx={{ flexGrow: 1 }} style={{paddingTop:50, paddingLeft:50, paddingRight:50, marginRight:30}}>
        <Grid container spacing={2}>
             <Grid item xs={4}>
                 <FormControl fullWidth>
                     <InputLabel id="demo-simple-select-label">Select Product</InputLabel>
                     <Select
                       labelId="demo-simple-select-label"
                       id="demo-simple-select"
                       value={selectedProduct}
                       label="Select Product"
                       onChange={handleChange}
                     >
                    {products.map((product) => (
                                <MenuItem key={product.id} value={product.id}>{product.displayName}</MenuItem>
                              ))}
                     </Select>
                 </FormControl>
                 </Grid>
             <Grid item xs={2}>
                <Button onClick={handleGetPrices} style={{backgroundColor:'#655c5c', marginTop:5}} size="large" variant="contained">GET PRICES</Button>
             </Grid>
             <Grid item xs={4} style={{marginTop:8}}>
                  <b>Last updated date:</b> {productPrices.lastUpdatedTime}
              </Grid>
              <Grid item xs={2}>
                  <Button disabled={!selectedProduct} onClick={handleRefresh} size="large" variant="contained">REFRESH</Button>
              </Grid>
        </Grid>

        <TableContainer component={Paper} style={{padding:50, marginTop:10, width:'97%'}}>
              <Table sx={{ minWidth: 650 }} size="small" aria-label="a dense table">
                <TableHead>
                  <TableRow>
                      <TableCell align="left">WEBSITE</TableCell>
                      <TableCell align="left">URL</TableCell>
                      <TableCell align="left">PRICE</TableCell>
                  </TableRow>
                </TableHead>
                <TableBody>
                  {productPrices.prices.map((productPrice) => (
                    <TableRow
                      key={productPrice.name}
                      sx={{ '&:last-child td, &:last-child th': { border: 0 } }}
                    >
                      <TableCell align="left">{productPrice.thirdPartyService}</TableCell>
                      <TableCell align="left"><a href={productPrice.url} target="_blank" style={{ fontSize: '12px' }}>{productPrice.url}</a></TableCell>
                      <TableCell align="left">{productPrice.price}</TableCell>
                    </TableRow>
                  ))}
                </TableBody>
              </Table>
            </TableContainer>
            <TablePagination
                      rowsPerPageOptions={15}
                      component="div"
                      count={productPrices.totalCount}
                      rowsPerPage={15}
                      page={page}
                      onPageChange={handleChangePage}
                    />

    </Box>

  );
}

export default App;
