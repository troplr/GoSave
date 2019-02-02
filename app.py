import dash
import dash_core_components as dcc
import dash_html_components as html
import pandas as pd
import plotly.graph_objs as go

header_names =[ 'sepal_length', 'sepal_width', 'petal_length', 'petal_width', 'class']
df = pd.read_csv(r'C:/Users/Harrison/Documents/PennApps/iris.csv.txt',names=header_names)

app = dash.Dash()
#External CSS
external_css = ["https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css",
                "https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css", ]
for css in external_css:
    app.css.append_css({"external_url": css})
#External JavaScript
external_js = ["http://code.jquery.com/jquery-3.3.1.min.js",
               "https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"]
for js in external_js:
    app.scripts.append_script({"external_url": js})
#Internal CSS
colors = {
         'background': '#0000FF',
         'color': '#FFA500'
}
#Our app's Layout
app.layout = html.Div(style=colors,children=[
    html.H1(children='Iris visualization',style={'textAlign':'center'}),
    html.Div(style={'textAlign':'center'},children='''
         Built with Dash: A web application framework for Python.
        '''),
    dcc.Graph(
        id='Iris Viz',
        figure={
            'data': [
                go.Scatter(
                    x=df[df['class'] == i]['petal_length'],
                    y=df[df['class'] == i]['sepal_length'],
                    mode='markers',
                    opacity=0.7,
                    marker={
                        'size': 15,
                        'line': {'width': 0.5, 'color': 'white'}
                    },
                    name=i
                ) for i in df['class'].unique()
            ],
            'layout': go.Layout(
                xaxis={'title': 'Petal Length'},
                yaxis={'title': 'Sepal Length'},
                margin={'l': 200, 'b': 40, 't': 100, 'r': 200},
                legend={'x': 0, 'y': 1},
                hovermode='closest'
            )
        }
    )
])

if __name__ == '__main__':
    app.run_server(debug=True)