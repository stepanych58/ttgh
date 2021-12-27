var path = require('path');

module.exports = {
    entry: './src/main/js/index.js',
    devtool: 'sourcemaps',
    cache: true,
    mode: 'development',
    resolve: {
        extensions: ['.js', '.jsx', '.ts', '.tsx'],
    },
    output: {
        path: __dirname,
        filename: './src/main/resources/static/built/bundle.js'
    },
    module: {
        rules: [
            {
                test: path.join(__dirname, '.'),
                exclude: /(node_modules)/,
                use: [{
                    loader: 'babel-loader',
                    options: {
                        presets: ["@babel/preset-env", "@babel/preset-react",
                            {
                                plugins: ['@babel/plugin-proposal-class-properties'
                                    , '@babel/plugin-transform-runtime'
                                ],
                            },]
                    }
                }]
            },
            {
                test: /\.css$/i,
                // loader: "css-loader",
                // options: {
                //     modules: {
                //         auto: true,
                //     },
                // },
                use: ["style-loader", "css-loader"],
            },
            {
                test: /\.(png|jpe?g|gif|svg)$/i,
                use: [
                    {
                        loader: 'file-loader',
                        options: {
                            name: '[name].[ext]',
                            esModule:false,
                            publicPath: 'images'
                        }
                    },
                ],

            },
        ]
    }
};
